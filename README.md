# ideas


get postgres IP:
```bash
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' ideas_postgres```
```

run application (replace IP):
```bash
java -Dspring.datasource.url=jdbc:postgresql://172.20.0.2:5432/ideas -Dideas.random.data=true -jar target/ideas-0.0.1-SNAPSHOT.jar
```

```graphql
query users {
  users {
    id
    firstName
    lastName
    posts {
			id	
      title
    }
    comments {
      content
      post {
        id
      }
    }
  }
}

query user {
  user(id: 2) {
    id
    firstName
    lastName
    posts {
      title
    }
  }
}

query posts {
  posts {
    id
    title
    comments {
      id
      content
      children {
        id
        content
      }
    }
  }
}

query post {
  post(id: 1) {
    id
    title
    comments {
      id
      content
      post {
        id
      }
      children {
        id
        content
      }
    }
  }
}

query comments {
  comments {
    id
    content
    author {
      firstName
      lastName
    }
    post {
      title
    }
    parent {
      id
    }
    children {
      id
      content
    }
  }
}

mutation createPerson {
  createPerson(user: {firstName: "John", lastName: "Doe"}) {
    id
    firstName
    lastName
    posts {
      title
    }
  }
}

mutation deletePerson {
  deletePerson(id: 8) {
    id
    firstName
    lastName
  }
}
```