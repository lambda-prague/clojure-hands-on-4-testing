(ns prague-lambda.hands4.red-black-test
  (:use prague-lambda.hands4.red-black)
  (:require [clojure.test :refer [deftest is are]]))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;;	deftest and is
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(deftest test-plus
  (is (= 2 (+ 1 1)))

  (are [x y] (= x y)
    2  (+ 1 1)
    4  (* 2 2)))
