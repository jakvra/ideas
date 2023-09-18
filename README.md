# ideas

```graphql
query persons {
  persons {
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

query person {
  person(id: 2) {
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
  createPerson(person: {firstName: "John", lastName: "Doe"}) {
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