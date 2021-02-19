# Clojurescript Firebase CRUD Demo

This demo app/tutorial aims to show that creating a Firebase app with Clojurescript doesn't
need any wrapper libraries. 

It has been created because this was the demo I wanted when getting started with Firebase and I thought 
the existing libs/tutorials I found could be improved on. Here is [in the accompanying blog post](https://widdindustries.com/clojurescript-firebase-simple/) that 
explains more of the rationale and goes into some implementation details.

It has been written with the docs and APIs from Firebase 8.2.7 - but Firebase version move pretty quickly
so that won't remain the latest for long. Still, the main point here is that not much code is required to 
join up Firebase in a Clojurescript app, not that this code is some major entity in its own right that you 
need to start from.

It also shows that [Re-Frame](https://github.com/day8/re-frame) apps don't need to put persistent state in the app-db (aka 'big atom').

## Usage

### Firebase setup

Start by following [the Firebase docs](https://firebase.google.com/docs/web/setup) to create your own app.

For this demo, enable:

* Google authentication
* Realtime database (choosing 'test rules' is fine for now)

In this repo, the 'firebase' dir contains a basic web page and is where Firebase CLI commands should 
be executed from.

* cd into the 'firebase' dir
* install firebase CLI globally `npm -i firebase -g`
* `firebase init`
* choose 'Hosting'
* accept 'public' for hosting dir
* don't overwrite index.html, don't configure as one-page app
* `firebase emulators:start`
* visit http://localhost:5000

The page you are viewing, `firebase/public/index.html` just includes some firebase sdks and initializes your app with 
the firebase backend 
   
Notice this file has the following in its `<body>`:

```html
  <div id="app" style="min-height:100%"></div>
  <script src="/cljs-out/main.js" type="text/javascript"></script>
```
* The 'app' div is where the Clojurescript with render to
* The script tag will load the compiled clojurescript.

### Include Clojurescript

Still with firebase emulator running, and from another terminal, run a clojure REPL from the top-level dir. 
This could be as simple as running `clj`,
but to get the most out of Clojure, you should be able to send commands to a REPL from
your editor. See [Practicalli](http://practicalli.github.io/clojure/clojure-editors/) for 
straightforward guides for many popular editors. 

In the REPL, type `(dev)`, this will start Clojurescript [live compilation](https://www.youtube.com/watch?v=KZjFVdU8VLI).

If you refresh your web page, you should see the todo list app and can start using it.

Open the file `dev/compilation.clj` in your editor. At the bottom there is a `(comment ..)` block - here are 
the commands you can send to the REPL to control Clojurescript compilation. Live compilation is already running,
so try sending `(cljs-repl)` to the REPL. That drops you into a new REPL, now using the browser environment.

Once in the browser REPL, open up `dev/dev/browser-scratch.cljs` to see some example commands you might want to 
send to effect changes in your app & etc

### Deploy to the web

Before deploying, you should create a minified build of the clojurescript code. If you're in a browser
repl, exit out with `:cljs/quit`. Once back in the main repl, send `(prod-build)` to the repl,
this compiles all of your clojurescript into one minified file.

When that completes, refresh your web page locally to see that it still works with the minified code.

When ready, run `firebase deploy` from the firebase dir.

That's it!

### Some points to note

I am not going to explain the code too much as there is not much of it and there are great docs for both Re-Frame/Clojurescript
and Firebase already. All this project is doing is putting the two together! Use the Firebase console
and the browser REPL to inspect the data model.

But... 

* No NPM? In this app, the firebase APIs are served by Firebase servers, and the React library has been
pre-bundled as part of a Clojurescript library. If we needed more libraries from NPM, I would probably
switch to the [Shadow](https://shadow-cljs.github.io/docs/UsersGuide.html) build tool to compile 
Clojurescript. That adds a little complexity, but is worth it if more is needed from NPM.

* There is no Clojurescript-Firebase wrapper library required. In this repo, the namespaces `firbase.auth` and 
`firebase.database` contain functions that call just the Firebase API functions that are needed for this app
and should look familiar if you've read some 'Getting Started' bits in the Firebase docs.

* The app never uses the Re-Frame 'app-db', which might be a surprise as it features quite large in most
explanations of Re-Frame. The app-db has its uses, but containing data from the Firebase database or auth would not
be one of them and is not required for our Re-Frame subscriptions. The implementation details are explained
[in the accompanying blog post](https://widdindustries.com/clojurescript-firebase-simple/)

