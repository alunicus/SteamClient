This is an open source project of Steam client based on open Valve APIs. The work is ongoing. Feel free to help :)

Possible improvements:

- Add Steam's front page as an initial client's screen
- Provide more information about the game on the game's screen
- Add the ability to watch game's videos
- Show reviews with filtering as a separate screen
- Add user login
- Implement user wishlist
- Adding a game to a wishlist
- Sales page with filtering

...and more and more features from Steam

Improvements in code base and development process:

- Possible moving to the Clean Architecture
- Adding of caching
- Fixing bug related to some games and products
- Add error handling
- Test, tests and tests
- A full description of the game comes as HTML from the server and must be extracted from the game screen to the separate screen
- Currently, search suggestions come as HTML and the app parses them. Need a better way how to manage it. Small backend could be a good solution
- Performance and leaks analysis is required
