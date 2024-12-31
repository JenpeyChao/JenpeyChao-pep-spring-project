package com.example.controller;

import static org.springframework.boot.SpringApplication.run;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 * 
 */
@RestController
public class SocialMediaController {
    @Autowired
    MessageService messageService;
    @Autowired 
    AccountService accountService;

    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message){
        System.out.println(this.accountService.findAccountByAccountId(message.getPostedBy()));
        if (message.getMessageText().length() >= 255 || message.getMessageText()=="" || this.accountService.findAccountByAccountId(message.getPostedBy()) ==null){
            return ResponseEntity.status(400).build();
        }
        
        return ResponseEntity.ok(this.messageService.createMessage(message));
        
    }
    @PostMapping("/register")
    public ResponseEntity<Account> createAccount(@RequestBody Account account){
        if(this.accountService.findAccountByUsername(account.getUsername())!= null){
            return ResponseEntity.status(409).build();
        }
        return ResponseEntity.ok(this.accountService.createAccount(account));
    }
    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account){
        Account res = this.accountService.findAccountByUsername(account.getUsername());

        if(res != null && account.getPassword().length()>4 && res.getPassword().equals(account.getPassword())){
            return ResponseEntity.ok(this.accountService.login(account));
        }
        
        return ResponseEntity.status(401).build();
        
        
    }
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<Integer> deleteMessageById(@PathVariable Integer messageId){
        int res = this.messageService.deleteMessageBymessageId(messageId);
        if(res ==1){
            return ResponseEntity.ok(res);
        }

        return ResponseEntity.ok().build();
    }
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getAllMessageFromUser(@PathVariable Integer accountId){
        List<Message> res = this.messageService.findAllMessageByPostedBy(accountId);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessage(){
        return ResponseEntity.ok(this.messageService.getAllMessage());
    }
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<Message> findMessageByMessageId(@PathVariable Integer messageId){
        return ResponseEntity.ok(this.messageService.findMessageByMessageId(messageId));
    }
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<Integer> updateMessageByMessageId(@PathVariable Integer messageId, @RequestBody Message message){
    
        String messageText = message.getMessageText();

        if (messageText == null || messageText=="" || messageText.isEmpty() || messageText.length() >= 255 || this.messageService.findMessageByMessageId(messageId) == null) {
            return ResponseEntity.status(400).build();
        }
        int res = this.messageService.patchMessageByMessageId(messageId, messageText);
        if (res > 0){
            return ResponseEntity.ok(this.messageService.patchMessageByMessageId(messageId, messageText));
        }else{
            return ResponseEntity.status(400).build();
        }
        
    }
}
