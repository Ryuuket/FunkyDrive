```mermaid
erDiagram
User ||--o{ File : owns
User ||--o{ Folder : owns
Folder ||--o{ File : contains


User {
UUID id
string email
string username
string password
date createdAt
}

File {
UUID userId
string title
text content
date datePosted
UUID userId
}

Folder {
UUID folderId
string title
text content
date datePosted
UUID fileId
}
```
  


