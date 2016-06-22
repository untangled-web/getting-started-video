(ns app.ui
  (:require [om.dom :as dom]
            [om.next :as om :refer-macros [defui]]
            yahoo.intl-messageformat-with-locales
            [untangled.client.core :as uc]))

(defui ^:once Root
  Object
  (render [this]
    (dom/div nil
      (dom/h4 nil "Header")
      (dom/p nil "TODO"))))

