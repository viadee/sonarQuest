# sonarQuest: Contribution Guide

First of all: Thanks for your interest in joining us on our journey throguh the world of sonarQuest!

The following is a set of guidelines for contributing to sonarQuest which is hosted by the viadee IT-Unternehmensberatung at GitHub. Guidelines are not meant to be rules. And as this is a community project - feel free to share your thoughts and experiences with the community and propose changes to this document in a pull request.

This project and everyone participating in it is governed by the sonarQuest [Code of Conduct] (https://github.com/viadee/sonarQuest/...). By participating, you are expected to uphold this code.

## How Can I Contribute?

* Reporting Bugs
* Suggesting Enhancements
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
* Also have a look at our [GitProject] (https://github.com/viadee/sonarQuest/projects) - maybe there are already cards you can turn into an issue. 
* open a new issue and label it as an enhancement  
* see how our contribution ist discussed and start writing oder testing code ;)

### Coding Guidelines

* styleguide: There are no strict styling rules. However, code should be easy to read as it makes reviewing a lot easier for everyone. 
  * the current state of the code is a good reference to start with
  * variables, functions, Parameters and constants should be given punchy names according to their function. This should be written in camelCase notation.  
  * remember: Code is usually 10 times more often read than changed
* no change without test: Please add UnitTests for every changed or newly added feature 
* there shouldn't be any TSLint warnings in the Angular parts of the code
* we embrace Clean Code so when in doubt - take a look at Uncle Bob's book or [blog](http://blog.cleancoder.com/). 

### Suggesting Pull Request

When you are adding features or other enhancements please create a pull request with a clear documentation of what you've done and why you have done it. It helps, if you make make sure all of your commits are atomic (one feature per commit).

Always include a useful log message for your commits. One-line messages are fine for small changes, but bigger changes should be commitetd with more comments, too.