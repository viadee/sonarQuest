![SonarQuest-Banner](docs/images/sonarquest-banner.png)


[![Continuous Integration](https://github.com/viadee/sonarQuest/actions/workflows/sonarquest-ci.yml/badge.svg?branch=master)](https://github.com/viadee/sonarQuest/actions/workflows/sonarquest-ci.yml)
[![](https://img.shields.io/github/release-pre/viadee/sonarquest.svg)](https://github.com/viadee/sonarQuest/releases) 
[![](https://img.shields.io/github/license/viadee/sonarquest.svg)](https://github.com/viadee/sonarQuest/blob/master/LICENSE)
[![](https://img.shields.io/github/stars/viadee/sonarQuest.svg)](https://github.com/viadee/sonarQuest/stargazers) 
[![](https://img.shields.io/github/forks/viadee/sonarQuest.svg)](https://github.com/viadee/sonarQuest/network/members)
[![](https://img.shields.io/github/contributors/viadee/SonarQuest.svg)](https://github.com/viadee/sonarQuest/graphs/contributors) 
[![](https://img.shields.io/github/last-commit/viadee/SonarQuest.svg)](https://github.com/viadee/sonarQuest/commits/master) 

**SonarQuest** is a gamification tool extending [SonarQube](https://www.sonarqube.org/),
that allows developers to get rid of their code quality issues in a playful way
by solving quests and adventures, earning rewards for refactoring code smells 
and optimization.

## Quick Start

### Install
Start your adventure with your team in 3 steps via Docker:
1. Clone this repo in a destination of your choice.
2. Run SonarQuest using Docker in the root project folder *sonarquest*: `docker-compose up` .
(_This will install the latest Docker images_)
4. We added some initial users to play around with.
   Open SonarQuest at http://localhost:4200 and login with the following credentials:

| Role               | Username | Password |
|--------------------|----------|----------|
| Admin              | admin    | test     |
| Game Master        | gm       | test     |
| Developer (Player) | dev      | test     |

Don't want to use Docker? Check out the [Installation Guide](https://github.com/viadee/sonarQuest/wiki/Installation)
in our wiki!

### Start the game

1. Log in as admin.
2. Connect to any reachable SonarQube server and check the connection.
3. Get all the projects on SonarQube into SonarQuest and make a single project playable as a "world".
4. Assign a game master and players to this world.
5. Log in as predefined game master or add a new game master.
6. Get all issues aka tasks for the current world.
7. Create quests by writing a short story and add tasks to make them solvable.
8. *(Optionally)* create an adventure and add quests to it to support a whole story.
9. Get your players to log in (all pre-made avatars have the password test and their username as login)
   and take on your quests by selecting to _fight_ in a quest and to _fight_ issues in the quests.
10. Login in as a game master from time to time to synchronize SonarQuest with the SonarQube project to have
    SonarQuest pay out rewards to your players!

Have fun!

Interested in getting to know SonarQuest more intense?
Check out our [GitHub wiki](https://github.com/viadee/sonarQuest/wiki)!

## Contributing
Interested in joining our adventure of making code refactorings much more fun? We are glad that you are here!
We are looking for enthusiasts and pioneers who want to be part of a motivated community,
regardless of whether you are designer, an idea generator or - of course - a developer: We welcome you!
Check out the [Contributing Guide](CONTRIBUTING.md) to get started.

![Example](docs/images/screenshot.jpg)

## Support
You have still open questions? Feel free to open an issue in GitHub. We will answer as fast as possible.


## Used frameworks
SonarQuest is a web-based app with an Angular UI application and Spring Boot as backend server.

* [Angular](https://angular.io) (licensed under MIT)
* [Angular Material](https://material.angular.io) (licensed under MIT)
* [Teradata Covalent](https://teradata.github.io/covalent/#/) (licensed under MIT)
* [RPG-Awesome](https://nagoshiashumari.github.io/Rpg-Awesome/) (licensed under BSD 2-Clause)
* [Node.js](https://nodejs.org/en/)
* [express.js](https://expressjs.com/) (licensed under MIT)
* [Spring Boot](https://spring.io/) (licensed under Apache License 2.0)


## License

This project is licensed under the BSD 3-Clause "New" or "Revised" License - see the [LICENSE](LICENSE) file for details.
The licenses of the reused components of the SonarQuest server can be found in [Licenses SonarQuestServer](sonarQuest-backend/src/main/resources/licenses/licenses.json).

