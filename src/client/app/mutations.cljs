(ns app.mutations
  (:require [untangled.client.mutations :as m]
            [untangled.client.core :as uc]
            [app.ui :as ui]
            [om.next :as om]))

(defmethod m/mutate 'app/add-item [{:keys [state ref]} k {:keys [label]}]
  {:action (fn []
             (let [list-path (conj ref :items)
                   new-item (uc/initial-state ui/Item {:label label})
                   item-ident (om/ident ui/Item new-item)]
               ; place the item in the db table of items
               (swap! state assoc-in item-ident new-item)
               ; tack on the ident of the item in the list
               (uc/integrate-ident! state item-ident :append list-path)))})
