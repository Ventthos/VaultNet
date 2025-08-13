import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import type { Category } from '../../models/category/Local/Category';

export function createSocketForBusiness(token:string, businessId:number, onCategoryEmission: (category:Category)=>void){
  const client = new Client({
    webSocketFactory: () => new SockJS(`http://localhost:8080/ws?token=${token}&businessId=${businessId}`),
    onConnect: () => {
      console.log('Conectado al WS');
      client.subscribe(`/topic/business/${businessId}/categories`, (message) => {
        const params = JSON.parse(message.body);
        onCategoryEmission(params);
      });
    },
    onWebSocketClose: (evt) => {
      console.log('WebSocket cerrado, code:', evt.code, 'reason:', evt.reason);
    }
  });

  client.activate();
  return client;
}

