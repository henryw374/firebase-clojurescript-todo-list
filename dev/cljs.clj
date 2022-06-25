(ns cljs
  (:require [com.widdindustries.tiado-cljs2 :as util]))

(defn test-watch []
  (util/browser-test-build :watch {}))

(defn app-config []
  (->
    (util/browser-app-config
      {:watch-dir "firebase/public"})
    (merge
      {:modules {:main {:entries ['com.widdindustries.demo-app.app]}}
       :js-options
       {:resolve {"react"     {:target :global :global "React"}
                  "react-dom" {:target :global :global "ReactDOM"}}}})))

(defn app-release []
  (util/prod-build
    (-> (app-config)
        (dissoc :devtools))))

(defn app-watch []
  (util/watch (app-config)))

(comment

  ; start compiling and watching the app
  (app-watch)
  ; visit http://localhost:9000 
  (util/repl)
  
  ; start up live-compilation of tests
  (test-watch)
  ; run cljs tests, having opened browser at test page (see print output of above "for tests, open...")
  (util/run-tests)
  ; start a cljs repl session in the test build. :cljs/quit to exit
  (util/repl :browser-test-build)

  ; do the release
  (app-release)

  (util/build-report (app-config) "build-report.html")
  
  ; you can stop/start etc as required
  (util/stop-server)

  )
