(ns invoice-spec
  (:require
    [clojure.spec.alpha :as s]))
(:require [clojure.test :refer [deftest is]]
  [invoice-item :as invoice-item])


;;PROBLEM 2
(defn not-blank? [value] (-> value clojure.string/blank? not))
(defn non-empty-string? [x] (and (string? x) (not-blank? x)))

(s/def :customer/name non-empty-string?)
(s/def :customer/email non-empty-string?)
(s/def :invoice/customer (s/keys :req [:customer/name
                                       :customer/email]))

(s/def :tax/rate double?)
(s/def :tax/category #{:iva})
(s/def ::tax (s/keys :req [:tax/category
                           :tax/rate]))
(s/def :invoice-item/taxes (s/coll-of ::tax :kind vector? :min-count 1))

(s/def :invoice-item/price double?)
(s/def :invoice-item/quantity double?)
(s/def :invoice-item/sku non-empty-string?)

(s/def ::invoice-item
  (s/keys :req [:invoice-item/price
                :invoice-item/quantity
                :invoice-item/sku
                :invoice-item/taxes]))

(s/def :invoice/issue-date inst?)
(s/def :invoice/items (s/coll-of ::invoice-item :kind vector? :min-count 1))

(s/def ::invoice
  (s/keys :req [:invoice/issue-date
                :invoice/customer
                :invoice/items]))


;;PROBLEM 3

;;test in subtotal function
(deftest test-subtotal-whithout-discount
         (let [item {:precise-quantity 6
                     :precise-price 50}
               result (invoice-item/subtotal item)]
              (is (= result 300))))

(deftest test-subtotal-whith-discount
         (let [item {:precise-quantity 6
                     :precise-price 50
                     :discount-rate 10}
               result (invoice-item/subtotal item)]
              (is (= result 270))))


(deftest test-subtotal-zero-discount
         (let [item {:precise-quantity 6
                     :precise-price 50
                     :discount-rate 0}
               result (invoice-item/subtotal item)]
              (is (= result 300))))

(deftest test-discount-factor
         (let [item {:precise-quantity 6
                     :precise-price 50
                     :discount-rate 10}
               result (invoice-item/discount-factor item)]
              (is (> result 0))))

(deftest test-discount-factor
         (let [item {:precise-quantity 6
                     :precise-price 50
                     :discount-rate 10}
               result (invoice-item/discount-factor item)]
              (is (> result 0))))

(deftest test-subtotal-zero-quantity
         (let [item {:precise-quantity 0
                     :precise-price 50
                     :discount-rate 10}
               result (invoice-item/subtotal item)]
              (is (= result 0))))