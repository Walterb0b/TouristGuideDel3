```mermaid
erDiagram
    TOURIST_ATTRACTION ||--o{ ATTRACTION_TAGS : has
    TOURIST_ATTRACTION {
        BIGINT attraction_id PK
        varchar(255) name
        varchar(100) City
        text description
        double price
 }
    
    ATTRACTION_TAGS }o--||TAGS : has
    ATTRACTION_TAGS {
        BIGINT attraction_id PK,FK
        BIGINT tag_id PK,FK
    }
    
    TAGS {
        BIGINT tag_id PK
        varchar(100) tag_name
    }
```
