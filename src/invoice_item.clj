;; (ns invoice-item)
(ns invoice_item
  (:gen-class))
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
;; (defn- discount-factor [{:invoice-item/keys [discount-rate]
;;                          :or                {discount-rate 0}}]
;;   (- 1 (/ discount-rate 100.0)))

;; (defn subtotal
;;   [{:invoice-item/keys [precise-quantity precise-price discount-rate]
;;     :as                item
;;     :or                {discount-rate 0}}]
;;   (* precise-price precise-quantity (discount-factor item)))

