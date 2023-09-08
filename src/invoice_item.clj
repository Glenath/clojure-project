
(ns invoice_item
  (:require [clojure.edn :as edn])
  (:require [clojure.data.json :as json]
            [clojure.java.io :as io])
    )

(defn -main [])


;;PROBLEM 1
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



(println "filtered items"  filter-items)



;;PROBLEM 2
(defn read-invoice [fileName]
  (with-open [invoice (io/reader fileName)]
    (json/read invoice)
             ))

(def newInvoice (read-invoice "invoice.json"))

(println "filtered-items"  newInvoice)



;;PROBLEM 3
 (defn- discount-factor [{:invoice-item/keys [discount-rate]
                          :or                {discount-rate 0}}]
   (- 1 (/ discount-rate 100.0)))

 (defn subtotal
   [{:invoice-item/keys [precise-quantity precise-price discount-rate]
     :as                item
     :or                {discount-rate 0}}]
   (* precise-price precise-quantity (discount-factor item)))

