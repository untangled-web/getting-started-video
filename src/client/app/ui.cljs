(ns app.ui
  (:require [om.dom :as dom]
            [om.next :as om :refer-macros [defui]]
            yahoo.intl-messageformat-with-locales
            [untangled.client.core :as uc]
            [untangled.client.mutations :as m]))

(defui ^:once Item
  static uc/InitialAppState
  (initial-state [clz {:keys [label]}] {:label label})
  static om/IQuery
  (query [this] [:label])
  static om/Ident
  (ident [this {:keys [label]}] [:items/by-label label])
  Object
  (render [this]
    (let [{:keys [label]} (om/props this)
          on-delete (om/get-computed this :on-delete)]
      (dom/li nil label (dom/button #js {:onClick #(on-delete label)} "X")))))

(def ui-item (om/factory Item {:keyfn :label}))

(defui ^:once MyList
  static uc/InitialAppState
  (initial-state [clz params] {:title             "Initial List"
                               :ui/new-item-label ""
                               :items             [(uc/initial-state Item {:label "A"})
                                                   (uc/initial-state Item {:label "C"})
                                                   (uc/initial-state Item {:label "B"})]})
  static om/IQuery
  (query [this] [:ui/new-item-label :title {:items (om/get-query Item)}])
  static om/Ident
  (ident [this {:keys [title]}] [:lists/by-title title])
  Object
  (render [this]
    (let [{:keys [title items ui/new-item-label] :or {ui/new-item-label ""}} (om/props this)
          on-delete (fn [label] (om/transact! this `[(app/delete-item {:label ~label}) :items/by-label]))]
      (dom/div nil
        (dom/h4 nil title)
        (dom/input #js {:value    new-item-label
                        :onChange (fn [evt] (m/set-string! this :ui/new-item-label :event evt))})
        (dom/button #js {:onClick #(om/transact! this `[(app/add-item {:label ~new-item-label}) :items/by-label])} "+")
        (dom/ul nil
          (map (fn [item] (ui-item (om/computed item {:on-delete on-delete}))) items))))))

(def ui-list (om/factory MyList))

(defui ^:once ItemCount
  static om/IQuery
  (query [this] [[:items/by-label '_]])
  Object
  (render [this]
    (let [{:keys [items/by-label]} (om/props this)
          n (count by-label)]
      (dom/span #js {:style #js {:float "right"}} n))))

(def ui-count (om/factory ItemCount))

(defui ^:once Root
  static uc/InitialAppState
  (initial-state [clz params] {:counter {}
                               :list    (uc/initial-state MyList {})})
  static om/IQuery
  (query [this] [:ui/react-key
                 {:counter (om/get-query ItemCount)}
                 {:list (om/get-query MyList)}])
  Object
  (render [this]
    (let [{:keys [ui/react-key list counter]} (om/props this)]
      (dom/div #js {:key react-key}
        (ui-count counter)
        (dom/h4 nil "Header")
        (ui-list list)))))
