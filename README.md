# Meeting Summarizer (Full Stack)

An AI-powered meeting notes summarizer and sharer.  
Built with Spring Boot (Java) for backend and React for frontend.

---

## Features

- Upload or paste meeting transcripts  
- Apply custom summarization instructions (e.g., "Summarize in bullet points")  
- AI generates structured summaries  
- Edit summaries before sharing  
- Share summaries via email (tested with MailHog/FakeSMTP for local dev)

---

##  Tech Stack

Backend: Spring Boot, Java, Maven  
Frontend: React (CRA)  
Email Service: Spring Boot Mail + MailHog (for local testing)  
AI Service: Groq / LLM API

---

##  Setup & Installation

### 1. Clone the Repo

```bash
git clone https://github.com/your-username/meeting-summarizer.git
cd meeting-summarizer
