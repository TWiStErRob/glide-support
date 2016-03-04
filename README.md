# Glide Support Application

Android application to test out ideas from the issues related to [Glide](https://github.com/bumptech/glide).

I use it to try the feasibility of my ideas which then make it to the issue description usually.
This is a fully functioning app that you can build and deploy to your phone and use as a source of Glide tricks.

---

> This repository is not meant to replace the Glide issue tracker, Glide library group or StackOverflow, it is meant to augment them and provide more publicly available Glide code examples.

> *Please refrain from opening issues here that are Glide related, use the other channels to get answers.*

---

Any pull requests are welcome if you can improve the quality or reproducibility of the ideas or just have some Glide related code you want to share. Feel free to create a new package under `random` and create an Activity or Fragment to demonstrate something.


## Structure

The application has two flavors `glide3` and `glide4` for `3.+` and `4.+` versions respectively. At the time of me writing this most of the issues and hence solutions are for Glide v3, because v4 is in alpha stage and it is not released yet.


### Issue Trackers
While I said it is for issue tracker ideas I also used it to try out ideas for other sources, here are the meanings of the packages:
 * `github/_<num>_<short_desc>` where `<num>` is a short integer:  
   **`https://github.com/bumptech/glide/issues/<num>`**
 * `groups/_<ID>_<short_desc>` where `<ID>` is an 11 digits long alphanumeric (including `_dash_` and `_`) ID  
   **`https://groups.google.com/forum/#!topic/glidelibrary/<ID>`**
 * `stackoverflow/_<num>_<short_desc>` where `<num>` is a long integer:  
   **`http://stackoverflow.com/questions/<num>`**
 * `random` are the ones I couldn't find the reference for or doing it out of curiosity

*All packages start with `_` because Java identifiers cannot start with numbers.*


## Development

I'm using **IntelliJ IDEA Ultimate 16 EAP** to maintain this so it'll likely "just work" with that, with anything else you're mostly on your own, but feel free to open an issue though if you need help.

It's using Google's official Gradle plugin to build, you can find the setup in app/build.gradle


### Prerequisites
 * Android SDK (see `build.gradle` for exact versions)
 * Gradle (using `gradlew` is recommended, but not necessary)


### Steps
 * Clone GitHub repository
 * Import from *Welcome screen* or *File > New > Project from existing sources*
 * Select from *External model: Gradle*
 * Finish wizard and wait for progress to finish
 * Choose `glide3Debug` or `glide4Debug` in *Build Variants*
 * Gradle Sync (may need an explicit sync on *View > Tool Windows > Gradle*)
 * Happy coding!


### Running a sample

Currently there's now way on UI to select which one to run so you'll have to modify the `AndroidManifest.xml` and set one or more of:
 * `application/meta-data[value='GlideModule'].name`  
   to a FQCN of a GlideModule in your chosen package
 * `activity/[name~='TestActivity']/meta-data[name='contentFragment'].value`  
   to a FQCN of a TestFragment in your chosen package
 * `activity/.name`  
   to a FQCN of a TestActivity in your chosen package

The default `QuickFragment` and `QuickModule` are there to quickly test if something would compile with potential of running it too.

## License and stuff

This repository is unlicensed, meaning you can do anything with it, but there are no guarantees.

This is not officially related to Glide and there's no guarantee that all issues will have some code here as there's no point in reproducing everyone's issues individually.
