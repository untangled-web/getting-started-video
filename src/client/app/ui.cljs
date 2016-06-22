(ns app.ui
  (:require [om.dom :as dom]
            [om.next :as om :refer-macros [defui]]
            yahoo.intl-messageformat-with-locales))

(defui ^:once Root
  Object
  (render [this]
    (dom/p nil "TODO")))

