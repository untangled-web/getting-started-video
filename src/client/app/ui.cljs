(ns app.ui
  (:require [om.dom :as dom]
            [om.next :as om :refer-macros [defui]]
            yahoo.intl-messageformat-with-locales
            [untangled.client.core :as uc]))

(defui ^:once Main
  static uc/InitialAppState
  (initial-state [clz params] {:id 1 :type :main-tab :extra "MAIN STUFF"})
  static om/IQuery
  (query [this] [:id :type :extra])
  Object
  (render [this]
    (let [{:keys [extra]} (om/props this)]
      (dom/p nil "Main: " extra))))

(def ui-main (om/factory Main {:keyfn :id}))

(defui ^:once Settings
  static uc/InitialAppState
  (initial-state [clz params] {:id 1 :type :settings-tab :args {:a 1}})
  static om/IQuery
  (query [this] [:id :type :args])
  Object
  (render [this]
    (let [{:keys [args]} (om/props this)]
      (dom/p nil "Settings: " (pr-str args)))))

(def ui-settings (om/factory Settings {:keyfn :id}))

(defui Switcher
  static uc/InitialAppState
  (initial-state [clz params] (uc/initial-state Main {}))
  static om/IQuery
  (query [this] {:main-tab (om/get-query Main) :settings-tab (om/get-query Settings)})
  static om/Ident
  (ident [this {:keys [type id]}] [type id])
  Object
  (render [this]
    (let [{:keys [type] :as props} (om/props this)]
      (case type
        :main-tab (ui-main props)
        :settings-tab (ui-settings props)
        (dom/p nil "NO TAB")))))

(def ui-switcher (om/factory Switcher))

(defui ^:once Root
  static uc/InitialAppState
  (initial-state [clz params] {:ui/react-key "start"
                               :tabs         (uc/initial-state Switcher {})})
  static om/IQuery
  (query [this] [:ui/react-key {:tabs (om/get-query Switcher)}])
  Object
  (render [this]
    (let [{:keys [ui/react-key tabs]} (om/props this)]
      (dom/div #js {:key react-key}
        (dom/h4 nil "Header")
        (dom/button #js { :onClick #(om/transact! this '[(app/choose-tab {:tab :main-tab})])} "Main")
        (dom/button #js { :onClick #(om/transact! this '[(app/choose-tab {:tab :settings-tab})])} "Settings")
        (ui-switcher tabs)))))
