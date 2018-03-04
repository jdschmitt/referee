# Sam's Referee

An Angular website built on top of Akka HTTP in a single repo, deployable to a single Heroku dyno.

## Development

To run backend and front-end seperately (to take advantage of live reload):
1. Comment out last line in built.sbt which adds front-end build as a compile dependency (optional but speeds things up)
2. Run API:
> sbt compile run
3. Run web app:
> npm run start
4. Open Chrome (OSx) to allow cross-origin stuff:
> open -a Google\ Chrome --args --disable-web-security --user-data-dir