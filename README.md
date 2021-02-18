# Clojurescript Firebase Demo

This demo app/tutorial aims to show that creating a Firebase app with Clojurescript doesn't
need any wrapper libraries. 

It also shows that Re-Frame apps don't need to put persistent state in the app-db (aka 'big atom').

## Usage

### Firebase setup

First, follow [the Firebase docs](https://firebase.google.com/docs/web/setup) to create your own app.

 For this demo, enable:

* Google authentication
* Realtime database (test rules fine for now)

In this repo, the 'firebase' dir contains a basic web page and is where Firebase CLI commands should 
be executed from.

* cd into 'firebase' dir
* install firebase CLI globally `npm -i firebase -g`
* `firebase init`
* choose Hosting
* accept 'public' for hosting
* don't overwrite index.html, don't configure as one-page app
* `firebase emulators:start`
* visit http://localhost:5000

The page you are viewing, `firebase/public/index.html` just includes some firebase sdks and initializes your app with firebase 
   
The file `firebase/public/index.html`  has the following in its `<body>`:

```html
  <div id="app" style="min-height:100%"></div>
  <script src="/cljs-out/main.js" type="text/javascript"></script>
```
The 'app' div is where the Clojurescript with render to and the script tag will load the 
compiled clojurescript.

### Include Clojurescript

Still with firebase emulator running, from another terminal, Run a clojure REPL from the top-level dir. 
This could be as simple as running `clj`,
but to get the most out of Clojure, you should be able to send commands to a REPL from
your editor. See [Practicalli](http://practicalli.github.io/clojure/clojure-editors/) for 
straightforward guides for many popular editors. 

In the REPL, type `(dev)` and then open the file `dev/compilation.clj` in your editor.

Go down to the bottom of the `dev/compilation.clj` where there is a `(comment ..)` block - here are 
the commands you will use to control Clojurescript compilation. Live compilation is already running,
so try sending `(cljs-repl)` to the REPL. That drops you into a REPL using the browser environment.

If you refresh your web page, you should see the todo list app and can start using it.

### Deploy to the web

Before deploying, you should do a production build of the clojurescript. If you're in a browser
repl, exit out with `:cljs/quit`. Once back in the main repl, send `(prod-build)` to the repl,
this compiles all of your clojurescript into a single file.

When that completes, refresh your web page locally to see that it still works with the prod-build code.

When ready, run `firebase deploy` from the firebase dir.

That's it!



