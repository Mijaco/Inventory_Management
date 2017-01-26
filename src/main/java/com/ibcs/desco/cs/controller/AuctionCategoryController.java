package com.ibcs.desco.cs.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ibcs.desco.common.service.CommonService;
import com.ibcs.desco.cs.model.AuctionCategory;
import com.ibcs.desco.cs.model.AuctionCategoryReference;
import com.ibcs.desco.inventory.model.ItemCategory;
import com.ibcs.desco.inventory.service.ItemGroupService;

/*
 * Author: Ihteshamul Alam
 * Programmer, IBCS
 */

@Controller
public class AuctionCategoryController {
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	ItemGroupService itemGroupService;
	
	@RequestMapping(value="/ac/auctionCategoryForm.do", method=RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView auctionCategoryForm() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			return new ModelAndView("centralStore/auctionCategory/auctionCategory");
		} catch( Exception e ) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("centralStore/error");
		}
	}
	
	@RequestMapping(value="/ac/auctionCategorySave.do", method=RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView auctionCategorySave( AuctionCategory auctionCategory ) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			auctionCategory.setId(null);
			auctionCategory.setActive(true);
			auctionCategory.setCreatedBy( commonService.getAuthUserName() );
			auctionCategory.setCreatedDate( new Date() );
			
			commonService.saveOrUpdateModelObjectToDB( auctionCategory );
			
			return new ModelAndView("redirect:/ac/auctionCategoryList.do");
		} catch( Exception e ) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("centralStore/error");
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/ac/auctionCategoryList.do", method=RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView auctionCategoryList() {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			List<AuctionCategory> auctionList = ( List<AuctionCategory> )  ( Object ) commonService
					.getAllObjectList("AuctionCategory");
			model.put("auctionList", auctionList);			
			return new ModelAndView("centralStore/auctionCategory/auctionCategoryList", model);
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("centralStore/error");
		}
	}
	
	@RequestMapping(value="/ac/showAuctionCategory.do", method=RequestMethod.GET)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView getShowAuctionCategory( AuctionCategory auctionCategory ) {
		return this.showAuctionCategory(auctionCategory);
	}
	
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/ac/showAuctionCategory.do", method=RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView showAuctionCategory( AuctionCategory auctionCategory ) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			AuctionCategory auctionCategoryList = ( AuctionCategory ) commonService
					.getAnObjectByAnyUniqueColumn( "AuctionCategory", "id", auctionCategory.getId().toString() );
			
			List<AuctionCategoryReference> auctionCategoryReferenceList = ( List<AuctionCategoryReference> ) ( Object )
					commonService.getObjectListByAnyColumn( "AuctionCategoryReference", "auctionCategory.id", auctionCategory.getId().toString() );
			
			List<ItemCategory> itemCategoryList = ( List<ItemCategory> ) ( Object ) itemGroupService
					.getAllItemGroups();
			
			List<AuctionCategoryReference> itemCategorySelected = ( List<AuctionCategoryReference> ) (Object) commonService
					.getAllObjectList("AuctionCategoryReference");

			model.put("auctionCategoryList", auctionCategoryList);
			model.put("itemCategoryList", itemCategoryList);
			model.put("auctionCategoryReferenceList", auctionCategoryReferenceList);
			model.put("itemCategorySelected", itemCategorySelected);
			
			return new ModelAndView("centralStore/auctionCategory/auctionCategoryShow", model);
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("centralStore/error");
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/ac/deleteAuctionCategory.do", method=RequestMethod.POST)
	@PreAuthorize("isAuthenticated()")
	public ModelAndView deleteAuctionCategory( AuctionCategory auctionCategory ) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			
			List<AuctionCategoryReference> auctionCategoryReferenceList = ( List<AuctionCategoryReference> ) ( Object )
					commonService.getObjectListByAnyColumn( "AuctionCategoryReference", "auctionCategory.id", auctionCategory.getId().toString() );

			//Delete Dtl Data
			for( int i = 0; i < auctionCategoryReferenceList.size(); i++ ) {
				Integer dtlId = auctionCategoryReferenceList.get(i).getId();
				commonService.deleteAnObjectById("AuctionCategoryReference", dtlId);
			}
			
			//Delete Category
			commonService.deleteAnObjectById("AuctionCategory", auctionCategory.getId() );

			return new ModelAndView("redirect:/ac/auctionCategoryList.do");
			
		} catch( Exception e ) {
			
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("centralStore/error");
		}
	}
	
	@RequestMapping(value="/ac/updateAuctionCategory.do", method=RequestMethod.POST)
	@ResponseBody
	@PreAuthorize("isAuthenticated()")
	public String updateAuctionCategory( AuctionCategory auctionCategory ) {
		String response = "";
		try {
			
			AuctionCategory auction = ( AuctionCategory ) commonService
					.getAnObjectByAnyUniqueColumn("AuctionCategory", "id", auctionCategory.getId().toString() );
			
			if( auction == null ) {
				response = "unsuccess";
			} else {
				
				auction.setName( auctionCategory.getName() );
				auction.setDescription( auctionCategory.getDescription() );
				auction.setRemarks( auctionCategory.getRemarks() );
				auction.setModifiedBy( commonService.getAuthUserName() );
				auction.setModifiedDate( new Date() );
				
				commonService.saveOrUpdateModelObjectToDB( auction );
				response = "success";
			}
			
		} catch( Exception e ) {
			e.printStackTrace();
			response = e.getMessage();
		}
		return response;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/ac/addAuctionCategoryReference.do", method = RequestMethod.POST)

	public ModelAndView addAuctionCategoryReference( ItemCategory itemCategory ) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			List<AuctionCategoryReference> checkList = ( List<AuctionCategoryReference> ) ( Object ) commonService
					.getObjectListByTwoColumn( "AuctionCategoryReference", "itemCategory.id", itemCategory.getCategoryId().toString(), "auctionCategory.id", itemCategory.getId().toString() );
			
			if( checkList.size() == 0 ) {
				AuctionCategoryReference dbInsert = new AuctionCategoryReference();
				
				ItemCategory itmCategory = ( ItemCategory ) commonService.getAnObjectByAnyUniqueColumn("ItemCategory", "categoryId", itemCategory.getCategoryId().toString() );
				AuctionCategory auction = ( AuctionCategory ) commonService.getAnObjectByAnyUniqueColumn( "AuctionCategory", "id", itemCategory.getId().toString() );
				
				dbInsert.setActive(true);
				dbInsert.setId(null);
				dbInsert.setItemCategory(itmCategory);
				dbInsert.setAuctionCategory(auction);
				dbInsert.setCreatedBy( commonService.getAuthUserName() );
				dbInsert.setCreatedDate( new Date() );
				commonService.saveOrUpdateModelObjectToDB(dbInsert);
			}
			
			return new ModelAndView("redirect:/ac/showAuctionCategory.do?id="+itemCategory.getId().toString());
			//return new ModelAndView("centralStore/auctionCategory/auctionCategoryShow", model);
		} catch( Exception e ) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("centralStore/error");
		}
	}

	@RequestMapping(value = "/ac/deleteAuctionCategoryReference.do", method = RequestMethod.POST)

	public ModelAndView deleteAuctionCategoryReference( AuctionCategoryReference auctionCategoryReference ) {
		Map<String, Object> model = new HashMap<String, Object>();
		try {
			Integer mstId = auctionCategoryReference.getMstId();
			
			commonService.deleteAnObjectById("AuctionCategoryReference", auctionCategoryReference.getId() );
			
			return new ModelAndView("redirect:/ac/showAuctionCategory.do?id="+mstId);
			//return new ModelAndView("centralStore/auctionCategory/auctionCategoryShow", model);
		} catch( Exception e ) {
			e.printStackTrace();
			model.put("errorMsg", e.getMessage());
			return new ModelAndView("centralStore/error");
		}
	}
}
