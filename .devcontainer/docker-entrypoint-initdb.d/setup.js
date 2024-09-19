db=db.getSiblingDB("mdd");
db.dropDatabase();

db.createCollection("topics",{
  "validator": {
     "$jsonSchema": {
       "bsonType": "object",
       "title": "Student Object Validation",
       "required": [
         "id",
         "name"
       ]
    }
  }
});

db.topics.createIndex({"id": 1}, {"unique": true});

db.runCommand({insert: "topics", documents:[
  {id: "java", name:"Java"},
  {id: "angular", name:"Angular"},
  {id: "spring-boot", name:"Spring-Boot"},
  {id: "mongodb", name:"MongoDB"},
  {id: "typescript", name:"TypeScript"},
  {id: "javascript", name:"JavaScript"}
]});


dbtest=db.getSiblingDB("test");
dbtest.dropDatabase();

dbtest.createCollection("topics",{
    "validator": {
       "$jsonSchema": {
         "bsonType": "object",
         "title": "Student Object Validation",
         "required": [
           "id",
           "name"
         ]
      }
    }
  });
  
dbtest.topics.createIndex({"id": 1}, {"unique": true});

dbtest.runCommand({insert: "topics", documents:[
  {id: "java", name:"Java"},
  {id: "angular", name:"Angular"},
  {id: "spring-boot", name:"Spring-Boot"},
  {id: "mongodb", name:"MongoDB"},
  {id: "typescript", name:"TypeScript"},
  {id: "javascript", name:"JavaScript"}
]});

dbtest.createUser(
{
    user: "test",
    pwd: "test",
    roles: [
        { role: "readWrite", db: "test" }
    ]
})