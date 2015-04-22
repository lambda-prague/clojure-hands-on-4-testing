(ns prague-lambda.hands4.red-black
  (:require [clojure.core.match :refer [match]]))

(defn tree-member? [tree value]
  (if tree
    (let [[_ left v right] tree]
      (cond
        (< value v)
        (tree-member? left value)

        (< v value)
        (tree-member? right value)

        :else
        true))
    false))

(defn tree-height [tree op]
  (if tree
    (let [[_ left _ right] tree]
      (inc (op (tree-height left op)
               (tree-height right op))))
    0))

(defn insert* [tree ivalue]
  "Inbalanced insert"
  (if tree
    (let [[color left v right] tree]
      (cond
        (< ivalue v)
        [:orange (insert* left ivalue) v right]

        (< v ivalue)
        [:orange left v (insert* right ivalue)]))
    [:orange nil ivalue nil]))

(defn tree* [s]
  (reduce insert* nil s))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;
;;	Red tree balance
;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn balance-red-black-insert [color left value right]
  (match [color left value right]
    [:black [:red [:red a x b] y c] z d]
    [:red [:black a x b] y [:black c z d]]

    [:black [:red a x [:red b y c]] z d]
    [:red [:black a x b] y [:black c z d]]

    [:black a x [:red b y [:red c z d]]]
    [:red [:black a x b] y [:black c z d]]

    [:black a x [:red [:red b y c] z d]]
    [:red [:black a x b] y [:black c z d]]

    :else
    [color left value right]))

(defn insert-red-black [tree ivalue]
  "Balanced red-black insert"
  (letfn [(insert [tree]
            (match [tree]
              [nil]
              [:red nil ivalue nil]

              [[color left v right]]
              (cond
                (< ivalue v)
                (balance-red-black-insert color (insert left) v right)

                (< v ivalue)
                (balance-red-black-insert color left v (insert right))

                :else
                tree)))]
    (let [[_ left v right] (insert tree)]
      [:black left v right])))

(defn tree->seq
  [tree]
  (if tree
    (let [[_ left v right] tree]
      (concat (tree->seq left) [v] (tree->seq right)))
    nil))


(defn tree-red-black [s]
  (reduce insert-red-black nil s))

(comment

  (def t1 (-> nil
              (insert* 1)
              (insert* 2)
              (insert* 3)
              (insert* 4)))

  (def t2 (reduce insert* nil (range 10)))




  (tree-height t2 min)
  (tree-height t2 max)

  (def t3 (-> nil
              (insert-red-black 1)
              (insert-red-black 2)
              (insert-red-black 3)
              (insert-red-black 4)
              ))

  (def t4 (reduce insert-red-black nil (range 16)))


  (tree-member? t4 5)

  (tree-member? t4 20)

  (tree-height t4 min)
  (tree-height t4 max)


  )
