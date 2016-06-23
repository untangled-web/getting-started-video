(ns app.api
  (:require [om.next.server :as om]
            [om.next.impl.parser :as op]
            [taoensso.timbre :as timbre]))

(defmulti apimutate om/dispatch)
(defmulti api-read om/dispatch)

(defmethod apimutate :default [e k p]
  (timbre/error "Unrecognized mutation " k))

(defmethod apimutate 'app/add-item [{:keys [db]} k {:keys [id label]}]
  {:action (fn []
             (let [items (:items db)
                   next-id (:next-id db)
                   new-id (swap! next-id inc)]
               (swap! items conj {:id new-id :label label})
               {:tempids {id new-id}}))})

(defmethod api-read :default [{:keys [ast query] :as env} dispatch-key params]
  (timbre/error "Unrecognized query " (op/ast->expr ast)))

(defmethod api-read :all-items [{:keys [db] :as env} dispatch-key params]
  {:value @(:items db)})
