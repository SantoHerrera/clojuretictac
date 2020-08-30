(ns tictac.core
  (:gen-class))

(use 'clojure.pprint)

;https://github.com/tbaik/clojure-ttt
;the ttt version I read before making this.
;I read it, liked it. Tried to forget it.
;then wrote this.
;I guessing they are very similer.
;if you want to go and read the original.



(declare getInput)

(defn square-matrix
  [n p]
  (->> p (repeat n) vec (repeat n) vec))

(def newState [[0 1 2] [3 4 5] [6 7 8]])


; (def state (atom (square-matrix 3 "E")))
(def state (atom newState))



(defn getNumbers
  [vec]
  (filter integer? vec))


(defn nextMark
  "takes 2d vector ex. [[1 2 3] ['x' 5 'o'] ['x' 'o' 9]]"
  [board]
  (let [allNums (mapcat getNumbers board)]
      (if (even? (count allNums))
        (str "O")
        (str "X"))))



(defn getCell
  [num]
  (let [y (quot num 3)
        x (- num (* y 3))]
    (vector y x)))


(defn contains-same-pieces
  [coll]
  (apply = coll))




(defn diagonalWinner?
  [board]
  (let [[r1 r2 r3] board
        [zero one two] r1
        [three four] r2
        [six seven eight] r3]
    (or (contains-same-pieces (vector zero four eight))
        (contains-same-pieces (vector two four six)))))





(defn hasHorizontalWinner?
  [board]
  (some true? (map contains-same-pieces board)))

(defn hasVerticalWinner?
  [board]
  (some true? (map contains-same-pieces (apply map vector board))))


(defn valid-moves
  [board]
  (filter integer? board))




(defn hasWinner?
  [board]
  (and (< (count (mapcat valid-moves board)) 5)
       (or (hasHorizontalWinner? board)
           (hasVerticalWinner? board)
           (diagonalWinner? board))))
(hasWinner? [["x" "o" "x"] ["o" "x" "o"] [6 7 8]])

(defn updateBoard
  [num mark]
  (let [cellCordinates (getCell num)
        y (get-in cellCordinates [0])
        x (get-in cellCordinates [1])]
    (if (number? (get-in @state [y x]))
       (swap! state assoc-in [y x] mark)
       (getInput "choose empty cell"))))

; (defn test
;   [num]
;   (cond (winner?) (do (println "ohhh") (println "yeah"))
;         (= 9 num) (println "fuck")
;         (> 0) (println "last")))

(defn movesAvailable
  [board]
  (count (mapcat valid-moves board)))



(defn gameV2
  [num]
  (updateBoard num (nextMark @state))
  (hasWinner? @state)
  (getInput))


(defn exitGame?
  [booleen]
  (and (= "e" booleen) (System/exit 0)))


;take away the do's and see if hasWinner still returns true
(defn gameOriginal
  [num]
  (updateBoard num (nextMark @state))
  (cond
    (hasWinner? @state)
    (do (println (str "player " (nextMark @state) "has won"))
        (exitGame? "e"))
    (= 0 (movesAvailable @state))
    (do (println "nobody won. run program to play again")
        (exitGame? "e")))
  (getInput))


(defn game
  [num]
  (let [currentMark (nextMark @state)]
    (updateBoard num currentMark)
    (cond
      (hasWinner? @state)
      (do (println (str "player " currentMark "has won"))
          (exitGame? "e"))
      (= 0 (movesAvailable @state))
      (do (println "nobody won. run program to play again")
          (exitGame? "e"))))
 (getInput))






;make it accept board and spit it out insead of using it head?
(defn updateBoardv2
  [num mark board]
  (let [cellCordinates (getCell num)
        y (get-in cellCordinates [0])
        x (get-in cellCordinates [1])]
    (if (number? (get-in board [y x]))
        (swap! board assoc-in [y x] mark)
        (println "choose empty cell"))))


;(def board [[0 1 2] [3 4 5] [6 7 8]])

; ;
; (defn gamev3
;   [num board]
;   (let [currentMark (nextMark board)]
;    (updateBoardv2 num currentMark board)
;    (cond
;      (hasWinner? board)
;      (println (str "player" (nextMark board) "has Won"))
;      (= 0 (movesAvailable board))
;      (println "nobody won run again to play"))))
;
; (gamev3 7 [["x" "o" "x"] ["o" "x" "o"] ["x" 7 8]])




(defn acceptableAnwser?
  "returns true if its a string digit thats between 0 - 8"
  [answer]
  (and
    (= 1 (count answer));if string is only one character
    (every? #(Character/isDigit %) answer);if character can be turned into number
    (not= "9" answer)))



(defn getInput
  ([] (println "choose cell " state)
   (flush)
   (let [numberEntered (read-line)]
     (exitGame? numberEntered)
     (if (acceptableAnwser? numberEntered)
       (game (Integer/parseInt numberEntered))
       (getInput "please type cell number, e - exit game"))))
  ([message] (println message)
   (getInput)))


(defn -main
  [& args]
  (getInput))



































;2 and 6 aswell


; (defn hasWinner?
;   [board]
;   (and (< (count (mapcat valid-moves board)) 5)
;        (or (hasHorizontalWinner? board)
;            (hasVerticalWinner? boar))))

;todo
;move the let block inside game function into updateBoard funciton



;what to do
;make state into [[0 1 2] [3 4 5] [6 7 8]]
; instead of being filled with E's


;
;
; (let [xWon true]
;   (case xWon
;     true (println "won" (nextMark @state))
;     false (println "lost like a mtfka")))
;
;
; (defn test
;   [num]
;   (cond->> num
;     (= 9 num) (getInput "why doesnt this workf")
;     (> num 99) (println "not his")
;     true (println "thous")))
;
; (test 9)
;
;
;
; (cond-> 9
;   true inc
;   true inc
;   false (* 8)
;   true (* 7))
;
;
;




;accept user input
;if its not a number ask for a number
;if its the number 9 ask for number 0 - 8
;  else println yayyyyy



;why does this work
; (defn ik [n p]
;   (->> p (repeat n) vec))


;and not this
;(defn idk [n p]
;  (p (repeat n) vec))
;(idk 3 3)
