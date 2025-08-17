import React, { useState } from "react";
import "./index.css";

function App() {
  const [transcriptText, setTranscriptText] = useState("");
  const [prompt, setPrompt] = useState("");
  const [summary, setSummary] = useState("");
  const [recipient, setRecipient] = useState(""); // new state for email
  const [loading, setLoading] = useState(false);

  const handleSummarize = async () => {
    if (!transcriptText.trim()) return;

    setLoading(true);
    setSummary("");

    try {
      const cleanText = (text) =>
        text.replace(/[\n\r\t]/g, " ").replace(/"/g, '\\"').trim();

      const response = await fetch("http://localhost:8080/api/summarize", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          transcriptText: cleanText(transcriptText),
          prompt: cleanText(prompt),
        }),
      });

      const data = await response.json();
      setSummary(data.summary || data.error || "No summary returned");
    } catch (error) {
      setSummary("Error: " + error.message);
    }
    setLoading(false);
  };

  // --- New function to send email ---
  const handleSendEmail = async () => {
    if (!recipient || !summary) {
      alert("Please enter recipient email and generate summary first.");
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/api/send-email", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          recipient,
          subject: "Meeting Summary",
          summary,
        }),
      });

      const data = await response.json();
      alert(data.message);
    } catch (error) {
      alert("Error sending email: " + error.message);
    }
  };

  return (
    <div className="container">
      <h1>📝 Meeting Summarizer</h1>

      <textarea
        placeholder="Paste meeting transcript here..."
        value={transcriptText}
        onChange={(e) => setTranscriptText(e.target.value)}
        rows="6"
      />

      <input
        type="text"
        placeholder="Summarization instructions (optional)"
        value={prompt}
        onChange={(e) => setPrompt(e.target.value)}
      />

      <div className="buttons">
        <button onClick={handleSummarize} disabled={loading}>
          {loading ? "Summarizing..." : "Summarize"}
        </button>
        <button
          onClick={() => {
            setTranscriptText("");
            setPrompt("");
            setSummary("");
            setRecipient("");
          }}
        >
          Clear
        </button>
      </div>

      {summary && (
        <div className="summary">
          <h3>📌 Summary:</h3>
          <pre>{summary}</pre>
          <button onClick={() => navigator.clipboard.writeText(summary)}>
            Copy
          </button>

          {/* New email input and send button */}
          <div className="email-section">
            <input
              type="email"
              placeholder="Recipient email"
              value={recipient}
              onChange={(e) => setRecipient(e.target.value)}
            />
            <button onClick={handleSendEmail}>Send Email</button>
          </div>
        </div>
      )}
    </div>
  );
}

export default App;
