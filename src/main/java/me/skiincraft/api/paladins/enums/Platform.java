package me.skiincraft.api.paladins.enums;

public enum Platform {

	/**
	 * <p>PC platform containing all PC id portal</p>
	 * <p>This platform does not exist in the Official API</p>
	 */
	PC(1, 5, 25, 28),

	/**
	 * <p>PS4 platform for Playstation players</p>
	 */
	PS4(9),

	/**
	 * <p>Xbox platform for Microsoft Xbox players</p>
	 */
	Xbox(10),

	/**
	 * <p>Switch platform for Nintendo Switch players</p>
	 */
	Switch(22),

	/**
	 * <p>Discord platform for PC players playing through Discord</p>
	 */
	Discord(25),

	/**
	 * <p>EpicGames platform for PC players playing through Epic Games launcher</p>
	 */
	EpicGames(28),

	/**
	 * <p>HiRez platform for PC players playing through Hirez launcher</p>
	 */
	HiRez(1),

	/**
	 * <p>Stream platform for PC players playing through Steam launcher</p>
	 */
	Steam(5);

	private final int[] portalid;
	Platform(int... portalid){
		this.portalid = portalid;
	}
	
	public static Platform getPlatformByName(String name) {
		for (Platform a:values()) {
			if (a.name().equalsIgnoreCase(name)) {
				return a;	
			}
		}
		return Platform.PC;
	}
	
	public static Platform getPlatformByPortalId(int portalId) {
		for (Platform a:values()) {
			for (int portalIds : a.getPortalId()) {
				if (portalIds == portalId) {
					return a;	
				}
			}
		}
		return Platform.PC;
	}
	
	public int[] getPortalId() {
		return portalid;
	}
	
}
