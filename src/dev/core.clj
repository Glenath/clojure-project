(ns dev.core
    (:require [clojure.edn :as edn])
    (:require [clojure.data.json :as json]))
(defn -main []
      (println "Hello, World!") (println "H9999lcccclo, World!"))
(def invoice (clojure.edn/read-string (slurp "invoice.edn")))

(defn filter-invoice [invoice]
      (->> invoice
           (:invoice/items)
           (filter #(or (and (:taxable/taxes %) (not (:retentionable/retentions %)))
                        (and (:retentionable/retentions %) (not (:taxable/taxes %)))))
           (filter #(or (and( :retentionable/retentions %) (some (fn [param1] (= 1 (:retention/rate param1))) ( :retentionable/retentions %)))
                        (and( :taxable/taxes %) (some (fn [param1] (= 16 (:tax/rate param1))) ( :taxable/taxes %))))
                   ))) ;

(def filter-items (filter-invoice invoice))



(println "filtered-items"  fielter-items)

