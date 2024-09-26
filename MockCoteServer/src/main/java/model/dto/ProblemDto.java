package model.dto;

public class ProblemDto {
	private int problem_id;
	private int difficulty;
	private String title;
	public int getProblem_id() {
		return problem_id;
	}
	public void setProblem_id(int problem_id) {
		this.problem_id = problem_id;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ProblemDto(int problem_id, int difficulty, String title) {
		super();
		this.problem_id = problem_id;
		this.difficulty = difficulty;
		this.title = title;
	}
	@Override
	public String toString() {
		return "ProblemDto [problem_id=" + problem_id + ", difficulty=" + difficulty + ", title=" + title + "]";
	}
}
