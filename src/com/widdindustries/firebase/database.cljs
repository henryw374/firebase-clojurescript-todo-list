(ns com.widdindustries.firebase.database
  (:require [re-frame.core :as rf]
            [reagent.core :as r]
            [reagent.ratom :as ratom]
            [cljs-bean.core :refer [->js ->clj]]
            [clojure.string :as string]))

(rf/reg-event-fx
  ::firebase-error
  (fn [_ [_ error]]
    (js/console.error (str "error:\n" error))))

(rf/reg-event-fx
  ::firebase-success
  (fn [_ [_]]
    (js/console.log (str "Write Succeeded"))))

(def default-pass-fail
  {:on-success [::firebase-success]
   :on-failure [::firebase-error]})

(defn success-failure-wrapper [args]
  (let [{:keys [on-success on-failure]} (merge default-pass-fail args)]
    (fn [err]
      (rf/dispatch
        (if (nil? err)
          on-success
          on-failure)))))

(defn ->path [p]
  (string/join "/" (->js p)))

(defn database-ref ^js [path]
  (-> (.database ^js js/firebase)
      (.ref (->path path))))

(defn- ref-set [{:keys [path value] :as args}]
  (.set (database-ref path)
    (->js value)
    (success-failure-wrapper args)))

(defn get-push-key [path]
  (let [push-key (-> (database-ref path)
                     (.push)
                     (.-key))]
    (concat path [push-key])))

(rf/reg-fx ::push-fx
  (fn [args]
    (ref-set
      (-> args
          (update :path get-push-key)))))

(rf/reg-event-fx
  ::push
  (fn [_ [_ args]]
    {::push-fx args}))

(defn on-value-reaction [{:keys [path] :as args}]
  (let [ref (database-ref path)
        reaction (r/atom nil)
        callback (fn [^js x] (reset! reaction (some-> x (.val) ->clj)))]
    (.on ref "value" callback (success-failure-wrapper args))
    (ratom/make-reaction
      (fn [] @reaction)
      :on-dispose #(do (.off ref "value" callback)))))

(rf/reg-sub ::realtime-value
  (fn [[_ args]]
    (on-value-reaction args))
  identity)