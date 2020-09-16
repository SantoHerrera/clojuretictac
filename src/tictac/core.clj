(ns tictac.core
  (:gen-class))

;https://github.com/tbaik/clojure-ttt
;the tictactoe version I read before making this.
;I read it, liked it. Tried to forget it.
;then wrote this.
;I'm guessing they are very similer.
;if you want to go and read the original.

;should I avoid using declare?

(declare getInput)

(declare exitGame?)

(def newState [[0 1 2] [3 4 5] [6 7 8]])

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

;this is ugly, make better
(defn diagonalWinner?
  [board]
  (let [[r1 r2 r3] board
        [zero one two] r1
        [three four] r2
        [six seven eight] r3]
    (or (contains-same-pieces (vector zero four eight))
        (contains-same-pieces (vector two four six)))))

(defn diagonalWinnerNew
  [board]
  (let [[r1 r2 r3] board
        [zero one two] r1
        [three four] r2
        [six seven eight] r3]
    (or (= (vector zero four eight))
        (= (vector two four six)))))

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

(defn movesAvailable
 [board]
 (count (mapcat valid-moves board)))


(defn acceptableAnwser?
  "returns true if its a string digit thats between 0 - 8"
  [answer]
  (and
    (= 1 (count answer));if string is only one character
    (every? #(Character/isDigit %) answer);if character can be turned into number
    (not= "9" answer)))

(defn exitGame?
  [lowerCaseE]
  (and (= "e" lowerCaseE) (System/exit 0)))

(defn printBoard
  [board]
  (doseq [a board]
   (println a)))


(defn update-board
  [num mark board]
  (if (number? (get-in board (getCell num)))
    (assoc-in board (getCell num) mark)
    board))

(defn get-input
  ([]
   (let [input (read-line)]
    (if (acceptableAnwser? input)
     input
     (get-input "please type only cell number"))))
  ([message]
   (println message)
   (get-input)))

; instead of if do condition
;cond
;hasWinner?
;(= 0 (movesAvailable game)
;user Input "e" - go ahead and exit game
(defn new-game
  []
  (loop [game newState
         moves-available (movesAvailable game)]
    (printBoard game)
    (if (= 0 (movesAvailable game))
      (println "nobody fucking won")
      (recur (update-board (Integer/parseInt (get-input)) (nextMark game) game) (dec moves-available)))))

(defn exit-game?
  [lower-case-e]
  (= "e" lower-case-e))


(defn exit-game
  []
  (System/exit 0))

;what i need to do is make it so that it only asks for input once
;where though
;
;
(defn new-gameV2
  []
  (loop [input (read-line)
         game newState
         moves-available (movesAvailable game)]
    (printBoard game)
    (cond
      (exit-game? input) (exit-game)
      (hasWinner? game) (println "fuck yeah youve won ")
      (= 0 (movesAvailable game)) (println "nobody fucking won"))
    (recur (read-line) (update-board (Integer/parseInt input) (nextMark game) game) (dec moves-available))))
;
; (def input (flush) (read-line))

(defn new-gameV3
  []
  (loop [all-inputs []
         game newState
         moves-available (movesAvailable game)]
    (printBoard game)
    (let [current-input (read-line)]
      (cond
        (exit-game? current-input) (exit-game)
        (hasWinner? game) (println "fuck yeah youve won ")
        (= 0 (movesAvailable game)) (println "nobody fucking won"))
      (recur (conj all-inputs current-input) (update-board (Integer/parseInt current-input) (nextMark game) game) (dec moves-available)))))



(defn readLines
  []
  (loop [lines []]
    (let [current-line (read-line)]
     (if (= current-line "")
      (println lines)
      (recur (conj lines current-line))))))





(defn -main
  [& args]
  (new-gameV2))


;if you enter a letter it breaks




;NEED TO printboard in command line after acceptableAnwser? fails


; ;need to if user enters e call exit game
;
; reason for let is so that it fixes
; situations like -> X wins but prints O has won





;clean up

;make it be able to run in lein run in command prompt





;todo
;make exit game but without exiting vm, and shutting down the vm



; (defn getInput
;   ([] (println "choose cell ")
;    (printBoard @state)
;    (flush)
;    (let [userInput (read-line)]
;      (exitGame? userInput)
;      (if (acceptableAnwser? userInput)
;        (game (Integer/parseInt userInput))
;        (getInput "please type cell number, e - exit game"))))
;   ([message] (println message)
;    (getInput)))




;reason for let is so that it fixes
; situations like -> X wins but prints O has won
; (defn game
;   [num]
;   (let [currentMark (nextMark @state)]
;     (updateBoard num currentMark)
;     (cond
;       (hasWinner? @state)
;       (do (println (str "player " currentMark "has won"))
;           (exitGame? "e"))
;       (= (movesAvailable @state) 0)
;       (do (println "nobody won. run program to play again")
;           (exitGame? "e"))))
;  (getInput))
