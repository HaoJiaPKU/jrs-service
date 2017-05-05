package cn.edu.pku.rec.gbdt;

import java.io.IOException;
import java.util.List;

import cn.edu.pku.search.domain.Education;
import cn.edu.pku.search.domain.ResumeJobpopo;
import cn.edu.pku.search.domain.WorkExperience;
import cn.edu.pku.util.FileInput;
import cn.edu.pku.util.HanLPSegmenter;

public class TextProcessor {

	public void fileToToken(String textPath, String outputPath) {
		FileInput fi = new FileInput(textPath);
		String content = new String();
		String line = new String ();
		try {
			while ((line = fi.reader.readLine()) != null) {
				content += line.trim();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fi.closeInput();
		
		stringToToken(content, outputPath);
	}
	
	public String [] stringToToken(String input, String outputPath) {
		return HanLPSegmenter.segmentation(input.trim(), true, true, outputPath);
	}
	
	public Instance makeInstanceForResume(ResumeJobpopo input, List<Education> edulist,
			List<WorkExperience> worklist, String outputPath, Model model) {
		String content = new String();
		content += " " + input.getSpeciality();
		content += " " + input.getOtherInfo();
		content += " " + input.getWorkingPlace();
		for(Education edu : edulist) {
			content += " " + edu.getAcademy();
			content += " " + edu.getDegree();
			content += " " + edu.getSchool();
			content += " " + edu.getMajor();
		}
		for(WorkExperience work : worklist) {
			content += " " + work.getCompany();
			content += " " + work.getJobTitle();
			content += " " + work.getDescription();
		}
		content += " " + input.getIndustryIntension();
		content += " " + input.getCategoryIntension();
		
		String [] tokens = HanLPSegmenter.segmentation(content.trim(), true, true, outputPath);
		return new Instance().makeInstance(tokens, model);
	}
	
	public Instance makeInstanceForPosition (String input, String outputPath, Model model) {		
		String [] tokens = HanLPSegmenter.segmentation(input.trim(), true, true, outputPath);
		return new Instance().makeInstance(tokens, model);
	}
}
