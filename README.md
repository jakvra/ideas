# ideas

#### execution using maven:
prerequisitie: maven, docker installed   
bellow command starts the PostgreSQL db within the docker container and connects to it
```shell
mvn spring-boot:run
```
within maven installed, it should be possible use the maven wrapper:
```shell
./mvnw spring-boot:run
```

#### Example graphql queries/mutations:
```graphql
query users {
    users {
        id
        firstName
        lastName
        dateOfBird
    }
}

query user {
    user(id: 1) {
        id
        firstName
        lastName
        dateOfBird
        posts(first: 2) {
            edges {
                node {
                    content
                }
            }
            pageInfo {
                hasPreviousPage
                hasNextPage
                startCursor
                endCursor
            }
        }
        comments(first: 2) {
            edges {
                node {
                    content
                  	children {
                      content
                    }
                }
            }
            pageInfo {
                hasPreviousPage
                hasNextPage
                startCursor
                endCursor
            }
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

query authorPosts {
    authorPosts(authorId: 1) {
        title
        content
        comments {
            content
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

mutation createUser {
    createUser(user: {firstName: "John", lastName: "Doe", dateOfBird:"2020-12-20"}) {
        id
        firstName
        lastName
    }
}

mutation deleteUser {
    deleteUser(id: 8) {
        id
        firstName
        lastName
    }
}
```