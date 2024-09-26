import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { NavbarComponent } from '../navbar/navbar.component';
import { WebsocketService } from '../../../services/websocket-service/websocket.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-messaging',
  standalone: true,
  imports: [CommonModule, FormsModule, NavbarComponent],
  templateUrl: './messaging.component.html',
  styleUrl: './messaging.component.css'
})
export class MessagingComponent implements OnInit {
  // messages: string[] = [];
  // messageContent: string = '';
  // username: string = '';
  
  constructor(private websocketService: WebsocketService) { }

  ngOnInit(): void {
    // this.websocketService.connect((message) => {
    //   this.onMessageReceived(message);
    // });
  }

  // sendMessage(): void {
  //   if (this.messageContent.trim() !== '') {
  //     const chatMessage = {
  //       sender: this.username,
  //       content: this.messageContent,
  //       type: 'CHAT'
  //     };
  //     this.websocketService.sendMessage(chatMessage);
  //     this.messageContent = '';
  //   }
  // }

  // addUser(): void {
  //   if (this.username.trim() !== '') {
  //     const chatMessage = {
  //       sender: this.username,
  //       type: 'JOIN'
  //     };
  //     this.websocketService.addUser(chatMessage);
  //   }
  // }

  // onMessageReceived(message: any): void {
  //   const chatMessage = JSON.parse(message.body);
  //   this.messages.push(`${chatMessage.sender}: ${chatMessage.content}`);
  // }
}
