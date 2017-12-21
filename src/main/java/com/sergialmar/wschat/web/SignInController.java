package com.sergialmar.wschat.web;

import com.sergialmar.wschat.config.ChatProperties;
import com.sergialmar.wschat.domain.SignInForm;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Log4j
@Controller
public class SignInController {

    @Autowired
    private ChatProperties chatProperties;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
}
