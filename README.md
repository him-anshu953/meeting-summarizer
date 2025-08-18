:contentReference[oaicite:0]{index=0}

---

###  Suggested Full README (with demo link and image)

Here’s an enhanced version of your README including a placeholder for an image (like a screenshot or logo) and a link to your live demo:

```markdown
# Meeting Summarizer (Full Stack)

<p align="center">
  <img src="assets/app-screenshot.png" alt="Meeting Summarizer Screenshot" width="600"/>
</p>

An AI-powered meeting notes summarizer and sharer.  
Built with Spring Boot (Java) for backend and React for frontend.

---

##  Features

- Upload or paste meeting transcripts  
- Apply custom summarization instructions (e.g., "Summarize in bullet points")  
- AI generates structured summaries  
- Edit summaries before sharing  
- Share summaries via email (tested with MailHog/FakeSMTP for local dev)

---

##  Tech Stack

- **Backend:** Spring Boot, Java, Maven  
- **Frontend:** React (Create React App)  
- **Email Service:** Spring Boot Mail + MailHog (local testing)  
- **AI Service:** Groq / LLM API

---

##  Demo

Check out the live version: [Meeting Summarizer Live Demo](https://meeting-summarizer-tau.vercel.app/)  
:contentReference[oaicite:1]{index=1}

---

##  Setup & Installation

```bash
git clone https://github.com/him-anshu953/meeting-summarizer.git
cd meeting-summarizer

# Backend
cd src
mvn clean install
mvn spring-boot:run

# In a new terminal – Frontend
cd ../summarizer-frontend
npm install
npm start
