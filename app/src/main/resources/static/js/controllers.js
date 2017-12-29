'use strict';

/* Controllers */

angular.module('springChat.controllers', ['toaster'])
    .controller('ChatController', ['$scope', '$location', '$interval', 'toaster', 'ChatSocket', '$http', function ($scope, $location, $interval, toaster, chatSocket, $http) {

        var typing = undefined;

        $scope.username = '';
        $scope.sendTo = 'nobody';
        $scope.sendToConversationId = '-1';
        $scope.conversations = [];
        $scope.messages = [];
        $scope.newMessage = '';

        $scope.refreshMessages = function () {
            if ($scope.sendTo != "nobody") {
                chatSocket.send("/app/messages/" + $scope.sendTo, {}, {})

            }
        };

        $scope.sendMessage = function () {
            var destination = "/app/chat.message";

            if ($scope.sendTo != "nobody" && $scope.newMessage.trim() != "") {
                destination = "/app/chat.private";
                $scope.messages.unshift({
                    message: $scope.newMessage,
                    username: 'you',
                    priv: true,
                    to: $scope.sendTo,
                    conversation: $scope.sendToConversationId
                });
                chatSocket.send(destination, {}, JSON.stringify({
                    message: $scope.newMessage,
                    conversation: $scope.sendToConversationId
                }));
                $scope.newMessage = '';
            }
        };

        $scope.startTyping = function () {
            // Don't send notification if we are still typing or we are typing a private message
            if (angular.isDefined(typing) || $scope.sendTo != "nobody") return;

            typing = $interval(function () {
                $scope.stopTyping();
            }, 500);

            chatSocket.send("/topic/chat.typing", {}, JSON.stringify({username: $scope.username, typing: true}));
        };

        $scope.stopTyping = function () {
            if (angular.isDefined(typing)) {
                $interval.cancel(typing);
                typing = undefined;

                chatSocket.send("/topic/chat.typing", {}, JSON.stringify({username: $scope.username, typing: false}));
            }
        };

        $scope.privateSending = function (id, username) {
            $scope.sendToConversationId = (id != $scope.sendToConversationId) ? id : '-1';
            $scope.messages = [];
            if (username != $scope.sendTo) {
                $http({method: 'GET', url: '/conversation/messages/' + $scope.sendToConversationId})
                    .then(function success(response) {
                        // console.log("---------------------------------");
                        // console.log(response.data);
                        // console.log("---------------------------------");
                        for (var index in response.data) {
                            var item = response.data[index];
                            $scope.messages.unshift(item);
                            //toaster.pop('success', "Message", item.message)
                        }
                    });
            }
            $scope.sendTo = (username != $scope.sendTo) ? username : 'nobody';

        };

        var initStompClient = function () {
            chatSocket.init('/ws');

            chatSocket.connect(function (frame) {

                $scope.username = frame.headers['user-name'];

                chatSocket.subscribe("/app/chat.conversations", function (message) {
                    $scope.conversations = JSON.parse(message.body);
                });

                chatSocket.subscribe("/topic/chat.login", function (message) {
                    //$scope.conversations.unshift({username: JSON.parse(message.body).username, typing: false});
                    toaster.pop('success', "Notification", "User " + JSON.parse(message.body).username + " has logged in")
                });

                chatSocket.subscribe("/topic/chat.logout", function (message) {
                    var username = JSON.parse(message.body).username;
                    toaster.pop('warning', "Notification", "User " + username + " has logged out")
                    // for (var index in $scope.conversations) {
                    //     if ($scope.conversations[index].name == username) {
                    //         $scope.conversations.splice(index, 1);
                    //     }
                    // }
                });

                chatSocket.subscribe("/topic/chat.typing", function (message) {
                    var parsed = JSON.parse(message.body);
                    if (parsed.username == $scope.username) return;

                    for (var index in $scope.conversations) {
                        var conversation = $scope.conversations[index];

                        if (conversation.username == parsed.username) {
                            $scope.conversations[index].typing = parsed.typing;
                        }
                    }
                });

                chatSocket.subscribe("/topic/chat.message", function (message) {
                    $scope.messages.unshift(JSON.parse(message.body));
                });

                chatSocket.subscribe("/user/exchange/amq.direct/chat.message", function (message) {
                    var parsed = JSON.parse(message.body);
                    if (parsed.conversation == $scope.sendToConversationId) {
                        parsed.priv = true;
                        $scope.messages.unshift(parsed);
                    }
                    toaster.pop('success', "Message", "You have a new message from " + parsed.username + " in conversation " + parsed.conversation)
                });

                chatSocket.subscribe("/user/exchange/amq.direct/errors", function (message) {
                    toaster.pop('error', "Error", message.body);
                });

            }, function (error) {
                toaster.pop('error', 'Error', 'Connection error ' + error);

            });
        };

        initStompClient();
    }]);