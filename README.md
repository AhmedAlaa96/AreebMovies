# Areeb Movies

The Areeb Movie App is an Android application that allows users to browse a list of trending movies and view detailed information about each movie. It follows the MVVM design pattern with the use of clean architecture principles. The app is written in Kotlin and supports a minimum SDK version of 24, targeting the latest Android version.

## Features

- Display a list of trending movies
- View detailed information about each movie
- Smooth and responsive user interface
- Efficient data fetching and caching
- Consistent and maintainable code structure


## Installation

1. Clone the repository from GitHub: git clone https://github.com/AhmedAlaa96/Movies
2. Open the project in Android Studio.
3. Build and run the app on an emulator or a physical device.

## Prerequisites

- Android Studio (version Electric Eel (2022.1.1) or higher)

## Technologies Used

- Kotlin
- Android Architecture Components (ViewModel, Flow Coroutines)
- Retrofit (for network requests)
- Glide (for image loading)
- Dependency Injection (Hilt)
- Unit Testing (JUnit, Mockito)

## Project Structure

The project follows the MVVM (Model-View-ViewModel) architecture pattern, combined with clean architecture principles. It is structured as follows:

- `app`: Contains the main Android application module, including activities, fragments, and UI-related code.
- `data`: Handles data-related operations, including data fetching, caching, and storage.
- `domain`: Defines the business logic and use cases of the application.
- `presentation`: Contains the ViewModels and UI-related logic for the app.
- `utils`: Contains utility classes and helper functions used throughout the project.

## Usage

1. Launch the app on your Android device or emulator.

2. The app will display a list of trending movies on the home screen.

3. Scroll through the list to browse the movies.

4. Tap on a movie to view more details, including the title, release date, overview, and other relevant information.

5. Use the back button to return to the list of movies.

## Testing

The project includes unit tests to ensure the correctness of the implemented features. You can run the tests using the following steps:

1. Open the project in Android Studio.

2. Navigate to the desired test class under the `test` directory.

3. Right-click on the test class and select "Run <TestClass>" to execute the tests.

[//]: # (## Release History)

[//]: # (- v1.0.0 &#40;2023-08-24&#41;: Initial release)

[//]: # (    - Implemented the main functionality of displaying the list of trending movies and showing movie details.)

[//]: # (- v1.0.1 &#40;2023-08-25&#41;: NDK Security & Fixing bugs)

[//]: # (    - Add NDK Security for the authorization token.)

[//]: # (    - Fixed minor bugs and improved overall stability.)

[//]: # (### You can find the latest build [here ↗]&#40;https://install.appcenter.ms/users/ahmedalaa/apps/movies/distribution_groups/public&#41;)

## Contributing

Contributions are welcome! If you find any bugs or have suggestions for improvements, please submit an issue or create a pull request.

## Acknowledgments

- [The Movie Database (TMDb) ↗](https://www.themoviedb.org/) - API used for fetching movie data.

## Contact

If you have any questions or inquiries, please contact:

Ahmed Alaa\
Email: ahmedalaahussein00@gmail.com\
LinkedIn: [Ahmed Alaa ↗](https://www.linkedin.com/in/ahmed-alaa-hussein/)
