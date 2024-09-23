const credentials = [
  { user: "test", pass: "test", db: "test" },
  { user: process.env["MONGO_USERNAME"], pass: process.env["MONGO_PASSWORD"], db: "mdd" },
]

const loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor. Cras elementum ultrices diam. Maecenas ligula massa, varius a, semper congue, euismod non, mi. Proin porttitor, orci nec nonummy molestie, enim est eleifend mi, non fermentum diam nisl sit amet erat.";

credentials.forEach(cr => {
  db = db.getSiblingDB(cr.db);
  db.dropDatabase();

  db.createCollection("topics", {
    validator: {
      $jsonSchema: {
        bsonType: "object",
        required: [
          "ref",
          "name",
          "description"
        ],
        properties: {
          ref: {
            bsonType: "string",
            description: "Unic topic reference name"
          },
          name: {
            bsonType: "string",
            description: "Topic's display name"
          },
          description: {
            bsonType: "string",
            description: "Topic's description"
          }
        }
      }
    }
  });

  db.topics.createIndex({ "ref": 1 }, { "unique": true });

  db.runCommand({
    insert: "topics", documents: [
      { ref: "java", name: "Java", description: loremIpsum },
      { ref: "angular", name: "Angular", description: loremIpsum },
      { ref: "spring-boot", name: "Spring-Boot", description: loremIpsum },
      { ref: "mongodb", name: "MongoDB", description: loremIpsum },
      { ref: "typescript", name: "TypeScript", description: loremIpsum },
      { ref: "javascript", name: "JavaScript", description: loremIpsum }
    ]
  });

  try {
    db.createUser(
      {
        user: cr.user,
        pwd: cr.pass,
        roles: [
          { role: "readWrite", db: cr.db }
        ]
      })

  } catch (error) {
    console.log(cr.user + " already exist on " + cr.db);
  }

});