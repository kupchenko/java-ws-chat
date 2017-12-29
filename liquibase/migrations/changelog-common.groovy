databaseChangeLog() {

    changeSet(id: 'chat-001', author: 'dmitrii_kupchernko') {
        createTable(tableName: "users") {
            column(name: "id", autoIncrement: "true", type: "INT") {
                constraints(primaryKey: "true")
            }
            column(name: "username", type: "VARCHAR(128)") {
                constraints(nullable: "false")
            }
            column(name: "password", type: "VARCHAR(128)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(id: 'chat-002', author: 'dmitrii_kupchernko') {
        createTable(tableName: "roles") {
            column(name: "id", autoIncrement: "true", type: "INT") {
                constraints(primaryKey: "true")
            }
            column(name: "name", type: "VARCHAR(45)") {
                constraints(nullable: "false")
            }
            column(name: "description", type: "VARCHAR(45)") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(id: 'chat-003', author: 'dmitrii_kupchernko') {
        createTable(tableName: "user_has_roles") {
            column(name: "id", autoIncrement: "true", type: "INT") {
                constraints(primaryKey: "true")
            }
            column(name: "user", type: "INT") {
                constraints(nullable: "false")
                constraints(foreignKeyName: "user_fk", references: "users(id)")
            }
            column(name: "role", type: "INT") {
                constraints(nullable: "false")
                constraints(foreignKeyName: "role_fk", references: "roles(id)")
            }
        }
    }

    changeSet(id: 'chat-004', author: 'dmitrii_kupchernko') {
        createTable(tableName: "conversations") {
            column(name: "id", autoIncrement: "true", type: "INT") {
                constraints(primaryKey: "true")
            }
            column(name: "name", type: "VARCHAR(128)") {
                constraints(nullable: "false")
            }
            column(name: "last_activity", type: "DATETIME") {
                constraints(nullable: "false")
            }
        }
    }

    changeSet(id: 'chat-005', author: 'dmitrii_kupchernko') {
        createTable(tableName: "user_has_conversations") {
            column(name: "id", autoIncrement: "true", type: "INT") {
                constraints(primaryKey: "true")
            }
            column(name: "conversation", type: "INT") {
                constraints(foreignKeyName: "conversation_fk", references: "conversations(id)", nullable: "false")
            }
            column(name: "participant", type: "INT") {
                constraints(foreignKeyName: "participant_fk", references: "users(id)", nullable: "false")
            }
        }
    }

    changeSet(id: 'chat-006', author: 'dmitrii_kupchernko') {
        createTable(tableName: "messages") {
            column(name: "id", autoIncrement: "true", type: "INT") {
                constraints(primaryKey: "true")
            }
            column(name: "text", type: "VARCHAR(1024)") {
                constraints(nullable: "false")
            }
            column(name: "sender", type: "INT") {
                constraints(foreignKeyName: "sender_fk", references: "users(id)", nullable: "false")
            }
            column(name: "conversation", type: "INT") {
                constraints(foreignKeyName: "conversation_message_fk", references: "conversations(id)", nullable: "false")
            }
            column(name: "timestamp", type: "DATETIME(6)") {
                constraints(nullable: "false")
            }
        }
    }
}