# Clojurescript Firebase Demo

This demo app aims to show that creating a Firebase app with Clojurescript is easy and doesn't
need any wrapper libraries. 

It also shows that Re-Frame apps don't need to put persistent state in the app-db (aka 'big atom').

## Usage

### Firebase setup

First, follow [the Firebase docs](https://firebase.google.com/docs/web/setup) to create your own app,
 setup hosting, create a directory (hereafter 'firebase directory') with a web page initialized with Firebase SDKs, deploy it to Firebase... basically 
 get to a deployed 'hello, world'.
 
 In this repo, the 'firebase' dir contains that simple web page and is where Firebase CLI commands like 
 `firebase init` are executed from. 
 
 For this demo, enable:

* Google authentication
* Realtime database

### Clojurescript setup

From here on, I'll assume you have cloned this repo and replaced its 'firebase' 
directory with the directory where you did your initial firebase web setup.
  
The web page you have needs to have the following added to the `<body>`:

```html
  <div id="app" style="min-height:100%"></div>
  <script src="/cljs-out/main.js" type="text/javascript"></script>
```
The 'app' div is where the Clojurescript with render to. The firebase/public/index.html page has this already.

 
 
### Develop Locally

Run a local emulator (`firebase emulators:start`) from your firebase directory and see that your page looks ok
on localhost. Note: first remove "rewrites" `**` in your firebase.json file, if you have those.

Run a clojure REPL from the top-level dir. This could be as simple as running `clj`,
but to get the most out of Clojure, you should be able to send commands to a REPL from
your editor. See [Practicalli](http://practicalli.github.io/clojure/clojure-editors/) for 
straightforward guides for many popular editors. 

In the REPL, type `(dev)` and then open the file `dev/web_dev.clj` in your editor.

Go down to the bottom of the file where there is a `(comment ..)` block - here are 
the commands you will use to control Clojurescript compilation. Start by sending
