(ns app.api
  (:require [om.next.server :as om]
            [om.next.impl.parser :as op]
            [taoensso.timbre :as timbre]))

(defmulti apimutate om/dispatch)
(defmulti api-read om/dispatch)

(def next-id (atom 2))
(def items (atom [{:id 1 :label "Item from Server"}]))

(defmethod apimutate :default [e k p]
  (timbre/error "Unrecognized mutation " k))

(defmethod apimutate 'app/add-item [e k {:keys [id label]}]
  {:action (fn []
             (Thread/sleep 1000)
             (let [new-id (swap! next-id inc)]
               (swap! items conj {:id new-id :label label})
               {:tempids {id new-id}}))})

(defmethod api-read :default [{:keys [ast query] :as env} dispatch-key params]
  (timbre/error "Unrecognized query " (op/ast->expr ast)))

(defmethod api-read :all-items [{:keys [query] :as env} dispatch-key params]
  (Thread/sleep 1000)
  {:value @items})
