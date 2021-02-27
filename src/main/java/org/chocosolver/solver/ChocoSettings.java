/*-
 * #%L
 * zebra4j
 * %%
 * Copyright (C) 2020 Marin Nozhchev
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */
package org.chocosolver.solver;

import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

import org.chocosolver.memory.Except_0;
import org.chocosolver.memory.ICondition;
import org.chocosolver.solver.constraints.real.Ibex;
import org.chocosolver.solver.search.strategy.Search;
import org.chocosolver.solver.search.strategy.strategy.AbstractStrategy;
import org.chocosolver.util.ESat;

@SuppressWarnings("rawtypes")
public class ChocoSettings implements Settings {

	/**
	 * Default welcome message
	 */
	private static final String DEFAULT_WELCOME_MESSAGE = "** Choco 4.10.6 (2020-12) : Constraint Programming Solver, Copyright (c) 2010-2020";

	private static final String DEFAULT_PREFIX = "TMP_";

	private String welcomeMessage = DEFAULT_WELCOME_MESSAGE;

	private Predicate<Solver> modelChecker = s -> !ESat.FALSE.equals(s.isSatisfied());

	private boolean enableViews = true;

	private int maxDomSizeForEnumerated = 32_768;

	private int minCardForSumDecomposition = 1024;

	private boolean enableTableSubstitution = true;

	private int maxTupleSizeForSubstitution = 10_000;

	private double MCRDecimalPrecision = 1e-4d;

	private boolean sortPropagatorActivationWRTPriority = true;

	private Function<Model, AbstractStrategy> defaultSearch = Search::defaultSearch;

	private ICondition environmentHistorySimulationCondition = new Except_0();

	private boolean warnUser = false;

	private boolean enableDecompositionOfBooleanSum = false;

	private IntPredicate enableIncrementalityOnBoolSum = i -> i > 10;

	private boolean cloneVariableArrayInPropagator = true;

	private String defaultPrefix = DEFAULT_PREFIX;

	private boolean enableSAT = false;

	private boolean swapOnPassivate = false;

	private boolean checkDeclaredConstraints = true;

	private boolean printAllUndeclaredConstraints = false;

	private byte hybridEngine = 0b00;

	private int nbMaxLearnt = 100_000;

	private int maxLearntCardinlity = Integer.MAX_VALUE / 100;

	private float clauseReductionRatio = .5f;

	private int dominancePerimeter = 4;

	private boolean explainGlobalFailureInSum = true;

	private double ibexContractionRatio = Ibex.RATIO;

	private boolean ibexRestoreRounding = Ibex.PRESERVE_ROUNDING;

	private Function<Model, Solver> initSolver = Solver::new;

	@Override
	public String getWelcomeMessage() {
		return welcomeMessage;
	}

	@Override
	public ChocoSettings setWelcomeMessage(String welcomeMessage) {
		this.welcomeMessage = welcomeMessage;
		return this;
	}

	@Override
	public boolean checkModel(Solver solver) {
		return modelChecker.test(solver);
	}

	@Override
	public ChocoSettings setModelChecker(Predicate<Solver> modelChecker) {
		this.modelChecker = modelChecker;
		return this;
	}

	@Override
	public boolean enableViews() {
		return enableViews;
	}

	@Override
	public ChocoSettings setEnableViews(boolean enableViews) {
		this.enableViews = enableViews;
		return this;
	}

	@Override
	public int getMaxDomSizeForEnumerated() {
		return maxDomSizeForEnumerated;
	}

	@Override
	public ChocoSettings setMaxDomSizeForEnumerated(int maxDomSizeForEnumerated) {
		this.maxDomSizeForEnumerated = maxDomSizeForEnumerated;
		return this;
	}

	@Override
	public int getMinCardForSumDecomposition() {
		return minCardForSumDecomposition;
	}

	@Override
	public Settings setMinCardinalityForSumDecomposition(int defaultMinCardinalityForSumDecomposition) {
		this.minCardForSumDecomposition = defaultMinCardinalityForSumDecomposition;
		return this;
	}

	@Override
	public boolean enableTableSubstitution() {
		return enableTableSubstitution;
	}

	@Override
	public ChocoSettings setEnableTableSubstitution(boolean enableTableSubstitution) {
		this.enableTableSubstitution = enableTableSubstitution;
		return this;
	}

	@Override
	public int getMaxTupleSizeForSubstitution() {
		return maxTupleSizeForSubstitution;
	}

	@Override
	public double getMCRDecimalPrecision() {
		return MCRDecimalPrecision;
	}

	@Override
	public Settings setMCRDecimalPrecision(double precision) {
		this.MCRDecimalPrecision = precision;
		return this;
	}

	@Override
	public ChocoSettings setMaxTupleSizeForSubstitution(int maxTupleSizeForSubstitution) {
		this.maxTupleSizeForSubstitution = maxTupleSizeForSubstitution;
		return this;
	}

	@Override
	public boolean sortPropagatorActivationWRTPriority() {
		return sortPropagatorActivationWRTPriority;
	}

	@Override
	public ChocoSettings setSortPropagatorActivationWRTPriority(boolean sortPropagatorActivationWRTPriority) {
		this.sortPropagatorActivationWRTPriority = sortPropagatorActivationWRTPriority;
		return this;
	}

	@Override
	public AbstractStrategy makeDefaultSearch(Model model) {
		return defaultSearch.apply(model);
	}

	@Override
	public ChocoSettings setDefaultSearch(Function<Model, AbstractStrategy> defaultSearch) {
		this.defaultSearch = defaultSearch;
		return this;
	}

	@Override
	public ICondition getEnvironmentHistorySimulationCondition() {
		return environmentHistorySimulationCondition;
	}

	@Override
	public ChocoSettings setEnvironmentHistorySimulationCondition(ICondition environmentHistorySimulationCondition) {
		this.environmentHistorySimulationCondition = environmentHistorySimulationCondition;
		return this;
	}

	@Override
	public boolean warnUser() {
		return warnUser;
	}

	@Override
	public ChocoSettings setWarnUser(boolean warnUser) {
		this.warnUser = warnUser;
		return this;
	}

	@Override
	public boolean enableDecompositionOfBooleanSum() {
		return enableDecompositionOfBooleanSum;
	}

	@Override
	public ChocoSettings setEnableDecompositionOfBooleanSum(boolean enableDecompositionOfBooleanSum) {
		this.enableDecompositionOfBooleanSum = enableDecompositionOfBooleanSum;
		return this;
	}

	@Override
	public boolean enableIncrementalityOnBoolSum(int nbvars) {
		return enableIncrementalityOnBoolSum.test(nbvars);
	}

	@Override
	public ChocoSettings setEnableIncrementalityOnBoolSum(IntPredicate enableIncrementalityOnBoolSum) {
		this.enableIncrementalityOnBoolSum = enableIncrementalityOnBoolSum;
		return this;
	}

	@Override
	public boolean cloneVariableArrayInPropagator() {
		return cloneVariableArrayInPropagator;
	}

	@Override
	public ChocoSettings setCloneVariableArrayInPropagator(boolean cloneVariableArrayInPropagator) {
		this.cloneVariableArrayInPropagator = cloneVariableArrayInPropagator;
		return this;
	}

	@Override
	@Deprecated
	public boolean enableACOnTernarySum() {
		return false;
	}

	@Override
	@Deprecated
	public Settings setEnableACOnTernarySum(boolean enable) {
		return this;
	}

	@Override
	public String defaultPrefix() {
		return defaultPrefix;
	}

	@Override
	public ChocoSettings setDefaultPrefix(String defaultPrefix) {
		this.defaultPrefix = defaultPrefix;
		return this;
	}

	@Override
	public boolean enableSAT() {
		return enableSAT;
	}

	@Override
	public ChocoSettings setEnableSAT(boolean enableSAT) {
		this.enableSAT = enableSAT;
		return this;
	}

	@Override
	public boolean swapOnPassivate() {
		return swapOnPassivate;
	}

	@Override
	public ChocoSettings setSwapOnPassivate(boolean swapOnPassivate) {
		this.swapOnPassivate = swapOnPassivate;
		return this;
	}

	@Override
	public boolean checkDeclaredConstraints() {
		return checkDeclaredConstraints;
	}

	@Override
	public ChocoSettings setCheckDeclaredConstraints(boolean checkDeclaredConstraints) {
		this.checkDeclaredConstraints = checkDeclaredConstraints;
		return this;
	}

	@Override
	public boolean printAllUndeclaredConstraints() {
		return printAllUndeclaredConstraints;
	}

	@Override
	public Settings setPrintAllUndeclaredConstraints(boolean printAllUndeclaredConstraints) {
		this.printAllUndeclaredConstraints = printAllUndeclaredConstraints;
		return this;
	}

	@Override
	public Solver initSolver(Model model) {
		return initSolver.apply(model);
	}

	@Override
	public ChocoSettings setInitSolver(Function<Model, Solver> initSolver) {
		this.initSolver = initSolver;
		return this;
	}

	@Override
	public byte enableHybridizationOfPropagationEngine() {
		return hybridEngine;
	}

	@Override
	public Settings setHybridizationOfPropagationEngine(byte hybrid) {
		this.hybridEngine = hybrid;
		return this;
	}

	@Override
	public int getNbMaxLearntClauses() {
		return nbMaxLearnt;
	}

	@Override
	public Settings setNbMaxLearntClauses(int n) {
		this.nbMaxLearnt = n;
		return this;
	}

	@Override
	public float getRatioForClauseStoreReduction() {
		return this.clauseReductionRatio;
	}

	@Override
	public Settings setRatioForClauseStoreReduction(float f) {
		this.clauseReductionRatio = f;
		return this;
	}

	@Override
	public int getMaxLearntClauseCardinality() {
		return maxLearntCardinlity;
	}

	@Override
	public Settings setMaxLearntClauseCardinality(int n) {
		maxLearntCardinlity = n;
		return this;
	}

	@Override
	public int getLearntClausesDominancePerimeter() {
		return dominancePerimeter;
	}

	@Override
	public Settings setLearntClausesDominancePerimeter(int n) {
		this.dominancePerimeter = n;
		return this;
	}

	@Override
	public boolean explainGlobalFailureInSum() {
		return explainGlobalFailureInSum;
	}

	@Override
	public Settings explainGlobalFailureInSum(boolean b) {
		this.explainGlobalFailureInSum = b;
		return this;
	}

	@Override
	public double getIbexContractionRatio() {
		return ibexContractionRatio;
	}

	@Override
	public void setIbexContractionRatio(double ibexContractionRatio) {
		this.ibexContractionRatio = ibexContractionRatio;
	}

	@Override
	public void setIbexRestoreRounding(boolean ibexRestoreRounding) {
		this.ibexRestoreRounding = ibexRestoreRounding;
	}

	@Override
	public boolean getIbexRestoreRounding() {
		return ibexRestoreRounding;
	}

}
