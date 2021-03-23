# sonarQuest: Contribution Guide

First of all: Thanks for your interest in joining us on our journey through the world of sonarQuest!

The following is a set of guidelines for contributing to sonarQuest which is hosted by the viadee IT-Unternehmensberatung at GitHub. Guidelines are not meant to be rules. And as this is a community project - feel free to share your thoughts and experiences with the community and propose changes to this document in a pull request.

## How Can I Contribute?

* Reporting Bugs
* Suggesting Enhancements
* Design Improvements 
* Your First Code Contribution
* Pull Requests

### Reporting Bugs

Before Submitting A Bug Report: Please take a look at the [current issues](https://github.com/viadee/sonarQuest/issues) and check wether the bug has already been reported and what the current state of the discussion about it might be.

Help the maintainers by giving detailed information on the bug and ways to reproduce the problem:

* a short but punchy title helps to categorize and locate the bug
* explain which behavior you observed and what was expected instead (and why).
* describe steps necessary to reproduce the problem, e.g. steps you took in the frontend-UI or data that was sent to the backend. 
* if there was an error message, please provide it completely, e.g. as a text file or a screenshot, with your issue
* include details about your configuration and environment  

### Suggesting Enhancements

sonarQuest aims to be a tool with an active community. So wether if your'e a developer wanting to slay dragons instead of solving issues or you want to send your team on an adventurous quest instead of assigning them maintainence task - we're open to your ideas for features and enhancements! 
Here are some hints on what helps to get those approved and implemented as quick as possible:

* check if it has already been [suggested](https://github.com/search?utf8=%E2%9C%93&q=+is%3Aissue+user%3Aviadee+repository%3AsonarQuest). If there are similar issues a discussion beforehand could make sense.
* Also have a look at our [GitProject](https://github.com/viadee/sonarQuest/projects) - maybe there are already cards you can turn into an issue. 
* open a new issue and label it as an enhancement  
* see how our contribution ist discussed and start writing oder testing code :wink:

### Coding

#### Requirements

 ##### Lombok
 This project uses [Lombok](https://projectlombok.org/) to avoid boilerplate code. While IntelliJ IDE users have an integrated support for Lombok (otherwise a plugin can be found in the in `File > Setting > Plugins`), Eclipse users have to install the extension manually.
 You can find the installation instructions for you IDE [here](https://projectlombok.org/setup/overview).
 Please open an issue, if there are any problems. 

#### Coding Guidelines

* styleguide: There are no strict styling rules. However, code should be easy to read as it makes reviewing a lot easier for everyone. 
  * the current state of the code is a good reference to start with
  * variables, functions, Parameters and constants should be given punchy names according to their function. This should be written in camelCase notation.  
  * remember: Code is usually 10 times more often read than changed
* no change without test: Please add UnitTests for every changed or newly added feature 
* there shouldn't be any TSLint warnings in the Angular parts of the code
* we embrace Clean Code so when in doubt - take a look at Uncle Bob's book or [blog](http://blog.cleancoder.com/). 

#### Branching
We want to keep the repository as clean and structured as possible. To achieve this, we use GitFlow as our Git workflow. Follow this [Link](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow "Gitflow Workflow | Atlassian Git Tutorial") to get more information about GitFlow. 
In short, this means the following for your collaboration on SonarQuest:

##### Feature Branch
If you want to add a great new feature to Sonar Quest or fix a major and minor issues, create a feature branch (`feature/<name>`) based on the `develop` branch. After you are done, please create a pull request to merge your feature into `develop`. After a bunch of new features and fixes, we will create a new release that includes your implementations.
![Gitflow - Feature Branches](docs/images/gitflow_feature.svg)
*Image source: https://wac-cdn.atlassian.com/dam/jcr:b5259cce-6245-49f2-b89b-9871f9ee3fa4/03%20(2).svg?cdnVersion=1515, no changes made*

##### Hotfix Branch
For quick patches of the release versions, please use hotfix branches (`hotfix/<name>`). If there is a bug or an error that needs to be fixed, you can create your hotfix branch as a fork of the `master`. After your fix is ready, please create a pull request to merge the branch into `develop` **and** `master`.
![Gitflow - Feature Branches](docs/images/gitflow_hotfix.svg)
*Image source: https://wac-cdn.atlassian.com/dam/jcr:61ccc620-5249-4338-be66-94d563f2843c/05%20(2).svg?cdnVersion=1515, no changes made*

For all other tasks around branching the SonarQuest core team takes care. If not everything is clear yet, feel free to have a look at the existing branches *(Be careful, there are also some older branches that don't conform to Gitflow yet. But you will recognize that)*. If you still have questions, feel free to contact us or open an issue. We will answer you as soon as possible. 

#### Suggesting Pull Request

When you are adding features or other enhancements please create a pull request as described in the previous [section](#branching), with a clear documentation of what you've done and why you have done it. It helps, if you make sure all of your commits are atomic (one feature per commit).

Always include a useful log message for your commits. One-line messages are fine for small changes, but bigger changes should be commitetd with more comments, too.

#### Pull Request - Admission tests

Pull requests will only be merged when all integration tests and all ui tests are green. To ensure this run
* the sonarquest-backend in your IDE of choice as Unit-Tests (this includes all integration tests) and
* npm test in the folder sonarquest-frontend to run Jasmine/Karme ui tests.
When all this works, submit your PR.
