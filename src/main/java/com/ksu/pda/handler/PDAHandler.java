package com.ksu.pda.handler;

import java.io.IOException;
import java.util.Stack;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;



@Component
public class PDAHandler extends TextWebSocketHandler{
	


	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		super.afterConnectionClosed(session, status);
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		super.afterConnectionEstablished(session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String[] textMessage = message.getPayload().split(",");
		String name = textMessage[0];
		String input = textMessage.length == 2 ? textMessage[1] : "";
		switch(name) {
			case "palindrome" :
				palaindrome(input, session);
				break;
			case "zeroNOneN" :
				zeroNOneN(input, session);
				break;
			case "CGLPalindrome" :
				CGLPalindrome(input, session);
				break;
		
		}
//		for (int i = 0; i < input.length(); i++) {
//			Thread.sleep(1000);
//			char c = input.charAt(i);
//			PDARelation relation = null;
//			boolean skip = false;
//			if(i == input.length() - 1) {
//				relation = currentState.getDelta().get("any");
//				if(relation != null) {
//					session.sendMessage(new TextMessage(name + "," +currentState.getName()+","+relation.getIndex()+"," + relation.getState().getName()));
//					currentState = relation.getState();
//					i = i-1;
//					skip = true;
//				}
//			}
//			if(!skip) {
//				relation = currentState.getDelta().get(""+c);
//				if(relation == null) {
//					relation = currentState.getDelta().get("any");
//					if(relation != null) {
//						session.sendMessage(new TextMessage(name + "," +currentState.getName()+","+relation.getIndex()+"," + relation.getState().getName()));
//						currentState = relation.getState();
//						i = i-1;
//					}else {
//						currentState = currentState.getFailedState();
//						session.sendMessage(new TextMessage(name + "," +currentState.getName()+","+0+"," + currentState.getName()));
//					}
//					
//				}else {
//					session.sendMessage(new TextMessage(name + "," +currentState.getName()+","+relation.getIndex()+"," + relation.getState().getName()));
//					currentState = relation.getState();
//					
//				}
//				
//			}
//			
//		}
//		Thread.sleep(1000);
//		session.sendMessage(new TextMessage(currentState.isAccepted() ? name+",Accepted,0,Accepted" : name+",failed,0,"+currentState.getName()));
	}
	
	private void palaindrome(String inputs, WebSocketSession session) throws IOException, InterruptedException {
		Stack<String> stack = new Stack<>();
		boolean even  = (inputs.length() % 2 == 0 );
		int mid =  (inputs.length()/2) + 1;
		int popIndex = even ? 1 : 2;
		for(int i = 0; i < inputs.length(); i++) {
			String s = ""+inputs.charAt(i);
			if(mid > i+1) {
				stack.push(s);
				session.sendMessage(new TextMessage("palindrome,start,1,start,push," + s +","+i));
			}else if (mid == i+1){
				if(even) {
					session.sendMessage(new TextMessage("palindrome,start,0,one,no," + s +","+i));
					Thread.sleep(1000);
					String last = stack.pop();
					if(last.equals(s)) {
						session.sendMessage(new TextMessage("palindrome,one,0,one,pop," + s +","+(i-popIndex)));
						popIndex = popIndex + 2;
					}else {
						session.sendMessage(new TextMessage("palindrome,failed,0,0,Expected : " + last + " got : " + s + ". " + inputs + " is not palindrome."));
						return;
					}
				}else {
					session.sendMessage(new TextMessage("palindrome,start,3,one,none," + s +","+i));
				}
			}else {
				String last = stack.pop();
				if(last.equals(s)) {
					session.sendMessage(new TextMessage("palindrome,one,0,one,pop," + s +","+(i-popIndex)));
					popIndex = popIndex + 2;
				}else {
					session.sendMessage(new TextMessage("palindrome,failed,0,0,Expected : " + last + " got : " + s + ". " + inputs + " is not palindrome."));
					return;
				}
			}
			
			Thread.sleep(1000);
		}
		session.sendMessage(new TextMessage("palindrome,Accepted,0,0," + inputs + " is a palindrome."));
	}
	
	private void zeroNOneN(String inputs, WebSocketSession session) throws IOException, InterruptedException {
		Stack<String> stack = new Stack<>();
		int popIndex = 1;
		for(int i = 0; i < inputs.length(); i++) {
			String s = ""+inputs.charAt(i);
			if(s.equals("0") ) {
				stack.push(s);
				session.sendMessage(new TextMessage("zeroNOneN,start,1,start,push," + s +","+i));
			}else {
				String last = stack.isEmpty() ? "" : stack.pop();
				if(last.equals("0")) {
					session.sendMessage(new TextMessage("zeroNOneN,one,0,one,pop,0,"+(i-popIndex)));
					popIndex = popIndex + 2;
				}else {
					session.sendMessage(new TextMessage("zeroNOneN,failed,0,0,Stack is Empty. We have More one than zero in " + inputs));
					return;
				}
			}
			
			Thread.sleep(1000);
		}
		if(stack.isEmpty()) {
			session.sendMessage(new TextMessage("zeroNOneN,Accepted,0,0, Equeal no of one and zero in " + inputs));
		}else {
			session.sendMessage(new TextMessage("zeroNOneN,failed,0,0,Stack is Not Empty. We have More Zero than one in " + inputs));
		}
		
	}
	
	private void CGLPalindrome(String inputs, WebSocketSession session) throws IOException, InterruptedException {
		boolean ret = true;
		int beginIndex = 0;
		int endIndex = inputs.length() -1;
		while(ret) {
			char a = inputs.charAt(beginIndex);
			char b = inputs.charAt(endIndex);
			String rem = inputs.substring(beginIndex, endIndex+1);
			if(a == b) {
				if(beginIndex != endIndex) {
					session.sendMessage(new TextMessage("CGLPalindrome,q0,"+rem + ", * S *"+ ","+beginIndex));
					Thread.sleep(1000);
				}else {
					session.sendMessage(new TextMessage("CGLPalindrome,q1,"+rem + ", S"+ ","+beginIndex));
					Thread.sleep(1000);
				}
				beginIndex++;
				endIndex--;
				if(beginIndex > endIndex) {
					Thread.sleep(1000);
					session.sendMessage(new TextMessage("CGLPalindrome,q2, , e"+ ","+beginIndex));
					session.sendMessage(new TextMessage("CGLPalindrome,Accepted,"+inputs + " is a palimdrome."));
					ret = false;
				}
			}else {
				session.sendMessage(new TextMessage("CGLPalindrome,failed,"+inputs + " is not a palimdrome."));
				ret = false;
			}
		}
	}
	


}
