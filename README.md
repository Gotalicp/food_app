# food_app
1. Welcome Screen with Navigation:Done
   
Create a welcome screen with navigation options to different sections of your app, such as "Search for Recipes," "My Favorites," and "Food Trivia."
Implement Android's navigation components to handle the navigation between these screens.

2. Recipe Search: To-Do
   
Implement a search feature that allows users to search for recipes by name, ingredients, or nutrients.
Use the /recipes/autocomplete endpoint from the Spoonacular API for name-based searching.
Throttle the search queries to avoid overusing the API key quota (e.g., 1-second delay between keystrokes).
Display search results in a RecyclerView with options to view recipe details or add recipes to favorites.

3. Recipe Details:Done
   
When a user selects a recipe from the search results, fetch and display detailed information about the recipe using the Spoonacular API.
Allow users to view recipe instructions step by step using a ViewPager with the /recipes/{id}/analyzedInstructions endpoint.

4. User Favorites:Done
   
Implement a feature that allows users to save their favorite recipes within the app.
Store favorite recipes locally in the app.
Use the /recipes/{id}/tasteWidget.json endpoint from Spoonacular to enrich favorite recipes with additional taste information. -ne baca

5. Food Trivia:Done
    
Create a screen that displays random food trivia using the /food/trivia/random endpoint from the Spoonacular API.
Implement food fact analysis using the /food/detect endpoint to extract keywords or tags from trivia.
Provide tag-type buttons related to the trivia fact, which, when clicked, pre-fill the search screen with the tag for recipe searching.

6. Themes and Settings:To-Do
    
Implement settings for users to customize the app's theme or other preferences.
Allow users to set a profile photo and username if desired.

7.History Page:Done

Implement history so u can track what recipes you have looked up.

8. Privacy and User Info:To-DO
   
Implement privacy settings to manage user data and preferences.
Allow users to view and edit their profile information.
