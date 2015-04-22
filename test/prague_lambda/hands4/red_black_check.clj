(ns prague-lambda.hands4.red-black-check
  (:use prague-lambda.hands4.red-black)
  (:require [clojure.test.check            :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;;	generators
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def random-input (gen/vector gen/int))

;; make samples
(gen/sample random-input)
(gen/sample (gen/map gen/keyword (gen/vector gen/int)))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;;	properties
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn balanced? [tree]
  (= (tree-height tree min)
     (tree-height tree max)))

(defn soso-balanced? [tree]
  (>= (* 2 (tree-height tree min))
      (tree-height tree max)))

(balanced? nil)
(balanced? (insert* nil 1))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;;      quick check
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(tc/quick-check 10
   (prop/for-all [v random-input]
     (balanced? (tree* v))))

(tc/quick-check 10
   (prop/for-all [v random-input]
     (soso-balanced? (tree* v))))

(tc/quick-check 100
  (prop/for-all [v random-input]
    (soso-balanced? (tree-red-black v))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;;	check if it is sorted
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(tc/quick-check 100
  (prop/for-all [v random-input]
    (= (seq (sort v))
       (tree->seq (tree-red-black v)))))
