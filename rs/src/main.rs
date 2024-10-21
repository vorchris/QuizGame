use std::fs::File;
use std::io::{self, BufReader, BufRead};
use std::path::Path;
use serde::Deserialize;
use rand::seq::SliceRandom;
use std::collections::HashMap;

#[derive(Deserialize)]
struct Question {
    question: String,
    options: Vec<String>,
    answer: usize,
}

fn load_questions(file_path: &str) -> io::Result<Vec<Question>> {
    let file = File::open(file_path)?;
    let reader = BufReader::new(file);
    let questions: Vec<Question> = serde_json::from_reader(reader)?;
    Ok(questions)
}

fn main() {
    let mut questions = Vec::new();
    let difficulties = vec!["easy", "normal", "hard"];
    for difficulty in difficulties {
        let file_path = format!("rs/src/questions/{}.json", difficulty);
        if let Ok(mut qs) = load_questions(&file_path) {
            questions.append(&mut qs);
        }
    }

    let mut rng = rand::thread_rng();
    questions.shuffle(&mut rng);

    let mut score = 0;
    for question in &questions {
        println!("{}", question.question);
        for (i, option) in question.options.iter().enumerate() {
            println!("{}. {}", i + 1, option);
        }

        let mut input = String::new();
        io::stdin().read_line(&mut input).expect("Failed to read line");
        let input: usize = input.trim().parse().expect("Please enter a number");

        if input == question.answer {
            score += 1;
            println!("Correct!");
        } else {
            println!("Wrong! The correct answer was: {}", question.options[question.answer - 1]);
        }
    }

    println!("Your final score is: {}", score);
}
