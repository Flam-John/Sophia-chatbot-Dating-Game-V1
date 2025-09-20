 **Relationship Chatbot Project Overview**

## **What is this project?**

This is a **web-based relationship chatbot** called **"Sophia"** - a 22-year-old art student persona designed to have natural, human-like conversations about relationships, psychology, personal growth, and emotional topics. It's built as a modern web application with a beautiful chat interface.

## **Technology Stack & Implementation**

### **Backend Technologies:**
- **Java 17** - Modern Java version
- **Spring Boot 3.2.5** - Main framework for web application
- **Spring Web** - REST API endpoints
- **Spring Thymeleaf** - Server-side templating
- **Maven** - Dependency management and build tool
- **OpenAI GPT-3.5-turbo** - AI language model for responses
- **Jackson** - JSON processing
- **Lombok** - Reduces boilerplate code
- **MapStruct** - Object mapping

### **Frontend Technologies:**
- **HTML5** - Structure
- **CSS3** - Modern styling with CSS variables and animations
- **Vanilla JavaScript** - Client-side functionality
- **Responsive Design** - Mobile-friendly interface

### **Development Tools:**
- **Spring Boot DevTools** - Hot reloading during development
- **Spring Boot Actuator** - Application monitoring
- **Spring Boot Validation** - Input validation

## **Project Architecture**

### **Class Descriptions:**

#### **🏗️ Main Application Class**
- **`RelationshipChatbotApplication.java`** - Spring Boot main class that starts the application

#### **🎮 Controller Layer**
- **`ChatController.java`** - REST API controller handling chat endpoints:
  - `POST /api/chat/send` - Processes user messages and returns AI responses
  - `POST /api/chat/clear` - Clears conversation history
  - Includes comprehensive logging and error handling

- **`WebController.java`** - Web controller serving the main chat interface:
  - `GET /` - Returns the main chat page

#### **⚙️ Service Layer**
- **`ChatService.java`** - Core business logic:
  - Manages conversation history
  - Defines Sophia's personality and system prompt
  - Processes messages and maintains context
  - Handles conversation clearing

- **`OpenAIService.java`** - OpenAI API integration:
  - Makes HTTP requests to OpenAI GPT-3.5-turbo
  - Handles authentication with API key
  - Processes responses and error handling
  - Includes comprehensive logging for debugging

#### **�� Model Layer**
- **`ChatMessage.java`** - Data model for chat messages:
  - Simple POJO with `role` and `content` fields
  - Used for both user messages and AI responses

#### **🔧 Configuration**
- **`WebConfig.java`** - CORS configuration:
  - Allows cross-origin requests for development
  - Configures allowed methods and headers

#### **�� Frontend**
- **`index.html`** - Complete chat interface:
  - Modern, responsive design with pink/purple theme
  - Real-time messaging with typing indicators
  - Mobile-friendly responsive layout
  - JavaScript handles API communication and UI updates

## **Key Features**

### **🤖 Sophia's Personality:**
- 22-year-old art student persona
- Warm, caring, emotionally intelligent
- Playful with occasional teasing
- Interests: psychology, relationships, personal growth, literature
- Natural, human-like responses (not robotic)
- Can be moody or quiet like a real person

### **💬 Chat Features:**
- Real-time messaging interface
- Typing indicators
- Message timestamps
- Conversation history management
- Error handling and user feedback
- Mobile-responsive design

### **🔒 Security & Configuration:**
- API key management through properties files
- CORS configuration for web requests
- Input validation and error handling
- Comprehensive logging for debugging

## **How It Works**

1. **User opens the web interface** at `http://localhost:8080`
2. **Sophia greets the user** with a welcome message
3. **User types messages** in the chat interface
4. **Frontend sends messages** to `/api/chat/send` endpoint
5. **ChatService processes** the message and maintains conversation history
6. **OpenAIService calls** OpenAI API with the conversation context
7. **Sophia responds** with her personality-driven reply
8. **Response is displayed** in the chat interface

This is a sophisticated chatbot that creates an engaging, human-like conversation experience focused on relationship and emotional topics! 💕

