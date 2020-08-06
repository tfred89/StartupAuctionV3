package com.stanfan.StartupAuctionV3.controller;

import java.security.Principal;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.stanfan.StartupAuctionV3.model.Bid;
import com.stanfan.StartupAuctionV3.model.BidDAO;
import com.stanfan.StartupAuctionV3.model.IllegalBidException;
import com.stanfan.StartupAuctionV3.model.LoginDTO;
import com.stanfan.StartupAuctionV3.model.Lot;
import com.stanfan.StartupAuctionV3.model.LotDAO;
import com.stanfan.StartupAuctionV3.model.Owner;
import com.stanfan.StartupAuctionV3.model.OwnerDAO;
import com.stanfan.StartupAuctionV3.model.Pass;
import com.stanfan.StartupAuctionV3.model.Player;
import com.stanfan.StartupAuctionV3.model.PlayerDAO;
import com.stanfan.StartupAuctionV3.model.RegisterUserDTO;
import com.stanfan.StartupAuctionV3.model.UserAlreadyExistsException;
import com.stanfan.StartupAuctionV3.security.jwt.JWTFilter;
import com.stanfan.StartupAuctionV3.security.jwt.TokenProvider;





@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = "*")
@RestController
public class AuctionController {
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private PlayerDAO playerDAO;
	private OwnerDAO ownerDAO;
	private BidDAO bidDAO;
	private LotDAO lotDAO;
	private static final Logger LOG = LoggerFactory.getLogger(AuctionController.class);
	
	public AuctionController(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder, 
			PlayerDAO playerDAO, OwnerDAO ownerDAO, BidDAO bidDAO, LotDAO lotDAO) {
		
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.playerDAO = playerDAO;
		this.ownerDAO = ownerDAO;
		this.bidDAO = bidDAO;
		this.lotDAO = lotDAO;

		
	}

	    // Forwards all routes to FrontEnd except: '/', '/index.html', '/api', '/api/**'
    // Required because of 'mode: history' usage in frontend routing, see README for further details
    @RequestMapping(value = "{_:^(?!index\\.html|api).$}")
    public String redirectApi() {
        LOG.info("URL entered directly into the Browser, so we need to redirect...");
        return "forward:/";
	}
	
	@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = "*")
    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDTO loginDto) {

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication, false);
        
        Owner user = ownerDAO.findByUsername(loginDto.getUsername());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JWTFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);
        return new ResponseEntity<>(new LoginResponse(jwt, user), httpHeaders, HttpStatus.OK);
    }
	@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = "*")
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/api/register", method = RequestMethod.POST)
    public void register(@RequestBody RegisterUserDTO newUser) {
        try {
            Owner user = ownerDAO.findByUsername(newUser.getUsername());
            throw new UserAlreadyExistsException();
        } catch (UsernameNotFoundException e) {
            ownerDAO.create(newUser.getUsername(),newUser.getPassword());
        }
    }
	//not actually sure what this is for.
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "api/test/user", method = RequestMethod.GET)
	public String userAccess() {
		return "User Content.";
	}
	
	@RequestMapping(path = "/api/players/{playerId}", method = RequestMethod.GET)
	public Player getPlayerById(@PathVariable int playerId) {
		Player player = playerDAO.getPlayerById(playerId);
		return player;
	}

	@RequestMapping(path = "/api/team/{ownerId}", method = RequestMethod.GET)
	public List<Player> getTeam(@PathVariable String ownerId) {
		List<Player> onTeam = playerDAO.getAllPlayersOnTeam(ownerId);
		return onTeam;
	}
	//add bid to bid list
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(path = "/api/bid", method = RequestMethod.POST)
	public Bid addBid(@RequestBody Bid bid) {
		//get latest bid for this player, make sure this bid is better.
		Bid bestBid = bidDAO.getHighestBid(bid.getPlayerId());
		if (bestBid.getContractVal() >= bid.getContractVal()) {
			throw new IllegalBidException();
		}
		return bidDAO.addBid(bid);
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(path = "/api/nominate", method = RequestMethod.POST)
	public Bid addFirstBid(@RequestBody Bid bid) {
		return bidDAO.addBid(bid);
	}
	
	@RequestMapping(path = "/api/bid/{bidId}", method = RequestMethod.GET)
	public Bid getBid(@PathVariable int bidId) {
		return bidDAO.getBidById(bidId);
	}
	
	//add owner to player when lot is won
	@RequestMapping(path = "/api/win/player/", method = RequestMethod.PUT)
	public void winAuction(@RequestBody @PathVariable int playerid, Owner winningOwner) {
		playerDAO.addOwnerToPlayer(playerid, winningOwner.getOwnerId());
	}
	
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(path = "/api/nominate", method = RequestMethod.PUT)
	public void tempOwnerForNomination(@RequestBody Player player) {
		playerDAO.addOwnerToPlayer(player.getPlayerId(), player.getOwnerId());
	}
	@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = "*")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(path = "/api/win/player", method = RequestMethod.PUT)
	public void updatePlayerAfterWin(@RequestBody Bid bid) {
		playerDAO.addInfoAfterWin(bid);
	}
	@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = "*")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(path = "/api/win/owner", method = RequestMethod.PUT)
	public void updateOwnerAfterWin(@RequestBody Bid bid) {
		ownerDAO.updateInfoAfterWin(bid);
		//return ownerDAO.getOwnerInfoById(bid.getBidderId());
	}
	@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = "*")
	@RequestMapping(path = "/api/auction/{playerid}", method = RequestMethod.GET)
	public Player getPlayerAuctionInfo(@PathVariable int playerid) {
		Player auctionPlayer = new Player();
		return auctionPlayer;
	}
	// get list of unowned players based off position
	//@CrossOrigin(origins = "http://localhost:8081")
	@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = "*")
	@RequestMapping(path = "/api/position/{position}", method = RequestMethod.GET)
	public List<Player> getAvailablePlayersByPosition(@PathVariable String position){
		
		return playerDAO.getAvailablePlayersAtPosition(position);
	}
	

	@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = "*")
	@RequestMapping(value = "/api/players", method = RequestMethod.GET)
	public List<Player> getAllPlayers(){
		return playerDAO.getAllPlayers();
	}

	@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = "*")
	@RequestMapping(value = "/api/lot/{lotId}", method = RequestMethod.GET)
	public Bid getBidInfoForLotDisplay(@PathVariable int lotId) {
		Bid b = new Bid();
		b = lotDAO.getBidByLotId(lotId);
		return b;
	}
	@CrossOrigin(origins = "http://localhost:8081", allowedHeaders = "*")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(value = "/api/lot/clear/{lotId}", method = RequestMethod.PUT)
	public void clearThisLot(@PathVariable int lotId) {
		lotDAO.clearLot(lotId);
	}
	
	@CrossOrigin(origins = "http://localhost:8081")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@RequestMapping(path = "/api/lot", method = RequestMethod.PUT)
	public void updateLotWithBid(@RequestBody Lot lot) {
		Lot l = new Lot();
		l.setBidId(lot.getBidId());
		l.setLotId(lot.getLotId());
		System.out.println(l.getLotId());
		System.out.println(l.getBidId());
		lotDAO.lotBid(l);
	}
	
	@RequestMapping(path = "api/owner/{ownerId}", method = RequestMethod.GET)
	public Owner getOwnerById(@PathVariable int ownerId){
		Owner o = new Owner();
		o = ownerDAO.getOwnerInfoById(ownerId);
		return o;
	}
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(path = "api/owner/persist", method = RequestMethod.GET)
	public Owner persistedLogin(Principal principal) {
		Owner o = new Owner();
		o = ownerDAO.getOwnerInfoByName(principal.getName());
		return o;
	}
	
	@RequestMapping(path = "api/owner/name/{ownerName}", method = RequestMethod.GET)
	public Owner getOwnerByName(@PathVariable String ownerName) {
		Owner o = new Owner();
		o = ownerDAO.getOwnerInfoByName(ownerName);
		return o;
	}
	
	@RequestMapping(path = "api/owner/scoreboard", method = RequestMethod.GET)
	public List<Owner> getOwnersForTable(){
		return ownerDAO.getAllOwners();
	}
	
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(path = "api/pass/{playerId}", method = RequestMethod.POST)
	public List<String> postNewPass(@PathVariable int playerId, Principal principal){
		return ownerDAO.addPass(playerId, principal.getName());
	}
	@RequestMapping(path = "api/pass/{playerId}", method = RequestMethod.GET)
	public List<String> getPasses(@PathVariable int playerId){
		return ownerDAO.getPassesById(playerId);
	}
	
    /**
     * Object to return as body in JWT Authentication.
     */
    static class LoginResponse {

        private String token;
        private Owner user;

        LoginResponse(String token, Owner user) {
            this.token = token;
            this.user = user;
        }

        @JsonProperty("token")
        String getToken() {
            return token;
        }

        void setToken(String token) {
            this.token = token;
        }

        @JsonProperty("user")
		public Owner getUser() {
			return user;
		}

		public void setUser(Owner user) {
			this.user = user;
		}
    }
}
