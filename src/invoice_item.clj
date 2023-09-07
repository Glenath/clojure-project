
(ns invoice_item
  (:require [clojure.edn :as edn])
  (:require [clojure.data.json :as json]
            [clojure.java.io :as io]))

(defn -main [])

;;PROBLEM 2
(defn read-invoice [fileName]
  (with-open [invoice (io/reader fileName)]
    (json/read invoice)
             ))

(def newInvoice (read-invoice "invoice.json"))

(println "filtered-items"  newInvoice)




 (defn- discount-factor [{:invoice-item/keys [discount-rate]
                          :or                {discount-rate 0}}]
   (- 1 (/ discount-rate 100.0)))

 (defn subtotal
   [{:invoice-item/keys [precise-quantity precise-price discount-rate]
     :as                item
     :or                {discount-rate 0}}]
   (* precise-price precise-quantity (discount-factor item)))

