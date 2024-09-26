import { Injectable } from '@angular/core';
// import { Client, Message, Stomp } from '@stomp/stompjs';
// import SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class WebsocketService {
  // private stompClient: Client;

  // constructor() {
  //   this.stompClient = new Client({
  //     brokerURL: 'wss://localhost:8443/ws',
  //     connectHeaders: {},
  //     debug: function (str) {
  //       console.log(str);
  //     },
  //     reconnectDelay: 5000,
  //     heartbeatIncoming: 4000,
  //     heartbeatOutgoing: 4000,
  //   });
  // }

  // connect(onMessageReceived: (message: Message) => void): void {
  //   this.stompClient.onConnect = (frame) => {
  //     this.stompClient.subscribe('/topic/public', onMessageReceived);
  //   };

  //   this.stompClient.activate();
  // }

  // sendMessage(message: any): void {
  //   this.stompClient.publish({
  //     destination: '/app/chat.sendMessage',
  //     body: JSON.stringify(message)
  //   });
  // }

  // addUser(user: any): void {
  //   this.stompClient.publish({
  //     destination: '/app/chat.addUser',
  //     body: JSON.stringify(user)
  //   });
  // }
}