# 🧠 spring-ai-mcp-server-resources

This project demonstrates how to expose **local documents as MCP (Model Context Protocol) resources** using **Spring AI**.  
It allows AI models (through the MCP protocol) to access files dynamically from a local directory — such as `.txt`, `.md`, `.json`, or other text-based documents.

---

## 🚀 Features

- 📁 Automatically registers all files in a given directory. 
- 🧩 Exposes each file as a **resource** via `McpServerFeatures.SyncResourceSpecification`  
- ⚙️ Simple and extensible Spring Boot setup  
- 🧠 Compatible with **Spring AI MCP Client** — resources can be discovered and read by an AI model at runtime  

---
